package kr.jay.webfluxcoroutine.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.date.after
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kr.jay.webfluxcoroutine.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.reactive.TransactionCallback
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@SpringBootTest
@ActiveProfiles("test")
class ArticleServiceTest(
    @Autowired private val sut: ArticleService,
    @Autowired private val repository: ArticleRepository,
    @Autowired private val rxtx: TransactionalOperator,
 ) : StringSpec({

    "get all"{
        rxtx.rollback {
            sut.create(ReqCreate(title = "test", body = "test", authorId = 1))
            sut.create(ReqCreate(title = "test", body = "test", authorId = 1))
            sut.create(ReqCreate(title = "test matched", body = "test", authorId = 1))
            sut.getAll().toList().size shouldBe 3
            sut.getAll("matched").toList().size shouldBe 1
        }
    }

    "create and get" {
        rxtx.rollback {
            val created = sut.create(ReqCreate(title = "test", body = "test", authorId = 1))
            val get = sut.get(created.id)
            get.id shouldBe created.id
            get.title shouldBe created.title
            get.authorId shouldBe created.authorId
            get.createdAt shouldNotBe null
            get.updatedAt shouldNotBe null
        }
    }

    "update"{
        rxtx.rollback {
            val created = sut.create(ReqCreate(title = "test", body = "test", authorId = 1))
            sut.update(created.id, ReqUpdate(title = "updated", body = "updated", authorId = 2))
            val updated = sut.get(created.id)
            updated.title shouldBe "updated"
            updated.body shouldBe "updated"
            updated.authorId shouldBe 2
        }
    }
    "delete"{
        rxtx.rollback {
            val prevCount = repository.count()
            val created = sut.create(ReqCreate(title = "test", body = "test", authorId = 1))
            repository.count() shouldBe prevCount + 1
            sut.delete(created.id)
            repository.count() shouldBe prevCount
        }
    }
})

suspend fun <T> TransactionalOperator.rollback(f: suspend (org.springframework.transaction.ReactiveTransaction) -> T): T{
   return this.executeAndAwait { tx ->
        tx.setRollbackOnly()
        f.invoke(tx)
    }
}
