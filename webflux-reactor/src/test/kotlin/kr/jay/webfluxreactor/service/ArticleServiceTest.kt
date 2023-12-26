package kr.jay.webfluxreactor.service

import kr.jay.webfluxreactor.repository.ArticleRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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

    ) {

    @Test
    fun createAndGet() {
        val preCount = repository.count().block() ?: 0
        val article = sut.create(ReqCreate(title = "test", body = "test", authorId = 1)).block()!!
        val currentCount = repository.count().block() ?: 0

        assertThat(preCount + 1L).isEqualTo(currentCount)

        val readArticle = sut.get(article.id).block()!!
        assertThat(article.id).isEqualTo(readArticle.id)
        assertThat(article.title).isEqualTo(readArticle.title)
        assertThat(article.body).isEqualTo(readArticle.body)
        assertThat(article.authorId).isEqualTo(readArticle.authorId)
        assertThat(readArticle.createdAt).isNotNull
        assertThat(readArticle.updatedAt).isNotNull
    }

    @Test
    fun getAll() {
        sut.create(ReqCreate(title = "test1", body = "test1", authorId = 1)).block()!!
        sut.create(ReqCreate(title = "test2", body = "test2", authorId = 1)).block()!!
        sut.create(ReqCreate(title = "test3 matched", body = "test3", authorId = 1)).block()!!

        assertThat(sut.getAll().collectList().block()!!.size).isEqualTo(3)
        assertThat(sut.getAll("matched").collectList().block()!!.size).isEqualTo(1)
    }

    @Test
    fun update(){
        val article = sut.create(ReqCreate(title = "test1", body = "test1", authorId = 1)).block()!!
        val request = ReqUpdate(title = "updated", body = "updated")

        sut.update(article.id, request).block()!!
        sut.get(article.id).block()!!.let { savedArticle ->
            assertThat(savedArticle.title).isEqualTo(request.title)
            assertThat(savedArticle.body).isEqualTo(request.body)
        }
    }

    @Test
    fun delete(){
        val preCount = repository.count().block() ?: 0
        val article = sut.create(ReqCreate(title = "test", body = "test", authorId = 1)).block()!!
        sut.delete(article.id).block()
        val currentCount = repository.count().block() ?: 0
        assertThat(preCount).isEqualTo(currentCount)

    }
}