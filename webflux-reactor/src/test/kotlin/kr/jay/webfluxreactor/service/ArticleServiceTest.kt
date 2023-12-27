package kr.jay.webfluxreactor.service

import kr.jay.webfluxreactor.repository.ArticleRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * ArticleServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */

@SpringBootTest
class ArticleServiceTest(
    @Autowired private val sut: ArticleService,
    @Autowired private val repository: ArticleRepository,
    @Autowired private val rxtx: TransactionalOperator,
) {

    @Test
    fun createAndGet() {

        sut.create(ReqCreate(title = "test", body = "test", authorId = 1)).flatMap { created ->
            sut.get(created.id).doOnNext { read ->
                assertThat(created.id).isEqualTo(read.id)
                assertThat(created.title).isEqualTo(read.title)
                assertThat(created.body).isEqualTo(read.body)
                assertThat(created.authorId).isEqualTo(read.authorId)
                assertThat(read.createdAt).isNotNull
                assertThat(read.updatedAt).isNotNull
            }
        }.rollback().block()

    }

    @Test
    fun getAll() {
        Mono.zip(
            sut.create(ReqCreate(title = "test1", body = "test1", authorId = 1)),
            sut.create(ReqCreate(title = "test2", body = "test2", authorId = 1)),
            sut.create(ReqCreate(title = "test3 matched", body = "test3", authorId = 1))
        ).flatMap {
            sut.getAll().collectList().doOnNext { all ->
                assertThat(all.size).isEqualTo(3)
            }
        }.flatMap {
            sut.getAll("matched").collectList().doOnNext { all ->
                assertThat(all.size).isEqualTo(1)
            }
        }.rollback()
    }

    @Test
    fun update() {
        val request = ReqUpdate(title = "updated", body = "updated")
        rxtx.execute { tx ->
            tx.setRollbackOnly()
            sut.create(ReqCreate(title = "test1", body = "test1", authorId = 1)).flatMap { new ->
                sut.update(new.id, request).flatMap { sut.get(new.id) }.doOnNext { updated ->
                    assertThat(updated.title).isEqualTo(request.title)
                    assertThat(updated.body).isEqualTo(request.body)
                }
            }
        }.blockLast()
    }

    @Test
    fun delete() {
        repository.count().flatMap { prevSize ->
            sut.create(ReqCreate(title = "test", body = "test", authorId = 1)).flatMap { new ->
                sut.delete(new.id).flatMap {
                    repository.count().doOnNext{ currentSize ->
                        assertThat(prevSize).isEqualTo(currentSize)
                    }
                }
            }
        }.rollback().block()
    }

    @Test
    fun deleteOnRollbackFunctionally(){
        repository.count().flatMap { prevSize ->
            sut.create(ReqCreate(title = "test", body = "test", authorId = 1)).flatMap { created ->
                Mono.zip(Mono.just(prevSize), Mono.just(created))
            }
        }.flatMap { context ->
            val created = context.t2
            sut.delete(created.id).flatMap{
                Mono.zip(Mono.just(context.t1), Mono.just(created))
            }
        }.flatMap{ context ->
            repository.count().flatMap{ currentSize->
                Mono.zip(Mono.just(context.t1), Mono.just(context.t2), Mono.just(currentSize))
            }
        }.doOnNext{
            val prevSize = it.t1
            val currentSize = it.t3
            assertThat(prevSize).isEqualTo(currentSize)
        }.rollback().block()
    }
}

@Configuration
class RxTransactionManager : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        rxtx = applicationContext.getBean(TransactionalOperator::class.java)
    }

    companion object {
        lateinit var rxtx: TransactionalOperator
            private set
    }
}


fun <T> Mono<T>.rollback(): Mono<T> {
    val publisher = this
    return RxTransactionManager.rxtx.execute { tx ->
        tx.setRollbackOnly()
        publisher
    }.next()
}

fun <T> Flux<T>.rollback(): Flux<T> {
    return RxTransactionManager.rxtx.execute { tx ->
        tx.setRollbackOnly()
        this
    }
}