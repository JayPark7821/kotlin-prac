package kr.jay.webfluxreactor.controller

import kr.jay.webfluxreactor.model.Article
import kr.jay.webfluxreactor.repository.ArticleRepository
import kr.jay.webfluxreactor.service.ReqCreate
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.event.annotation.AfterTestClass
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.temporal.ChronoUnit

/**
 * ArticleControllerTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */

private val logger = KotlinLogging.logger {}

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DirtiesContext
class ArticleControllerTest(
    @Autowired private val context: ApplicationContext,
    @Autowired private val repository: ArticleRepository,
) {

    val client = WebTestClient.bindToApplicationContext(context).build()

    @AfterTestClass
    fun clean(){
        repository.deleteAll()
    }

    @Test
    fun create() {
        val request = ReqCreate("test", "it is r2dbc demo", 1234)
        client.post().uri("/article").accept(MediaType.APPLICATION_JSON).bodyValue(request).exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("title").isEqualTo(request.title)
            .jsonPath("body").isEqualTo(request.body)
            .jsonPath("authorId").isEqualTo(request.authorId)
    }

    @Test
    fun get() {
        val request = ReqCreate(title = "title 1", body = "it is r2dbc demo", authorId = 1234)
        val created = client.post().uri("/article").accept(APPLICATION_JSON)
            .bodyValue(request).exchange().expectBody(Article::class.java).returnResult().responseBody!!
        val read = client.get().uri("/article/${created.id}").accept(APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody(Article::class.java).returnResult().responseBody!!

        Assertions.assertThat(created.title).isEqualTo(read.title)
        Assertions.assertThat(created.body).isEqualTo(read.body)
        Assertions.assertThat(created.authorId).isEqualTo(read.authorId)
        Assertions.assertThat(created.createdAt?.truncatedTo(ChronoUnit.MILLIS))
            .isEqualTo(read.createdAt?.truncatedTo(ChronoUnit.MILLIS))
        Assertions.assertThat(created.updatedAt?.truncatedTo(ChronoUnit.MILLIS))
            .isEqualTo(read.updatedAt?.truncatedTo(ChronoUnit.MILLIS))
    }

    @Test
    fun getAll() {
        repeat(5) { i ->
            val request = ReqCreate(title = "title $i", body = "it is r2dbc demo", authorId = 1234)
            client.post().uri("/article").accept(APPLICATION_JSON).bodyValue(request).exchange()
                .expectBody(Article::class.java).returnResult().responseBody!!
        }
        client.post().uri("/article").accept(APPLICATION_JSON).bodyValue(
            ReqCreate(title = "title matched", body = "it is r2dbc demo", authorId = 1234)
        ).exchange()

        val count = client.get().uri("/article/all").accept(APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody(List::class.java)
            .returnResult().responseBody?.size ?: 0

        Assertions.assertThat(count).isGreaterThan(0)

        client.get().uri("/article/all?title=matched").accept(APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
    }

    @Test
    fun update(){
        val created = client.post().uri("/article").accept(APPLICATION_JSON)
            .bodyValue(ReqCreate(title = "title 1", body = "it is r2dbc demo", authorId = 1234))
            .exchange().expectBody(Article::class.java).returnResult().responseBody!!

        client.put().uri("/article/${created.id}").accept(APPLICATION_JSON)
            .bodyValue(ReqCreate(title = "updated", body = "updated", authorId = 1234))
            .exchange().expectStatus().isOk
            .expectBody()
            .jsonPath("title").isEqualTo("updated")
            .jsonPath("body").isEqualTo("updated")
            .jsonPath("authorId").isEqualTo(1234)
    }

    @Test
    fun delete(){
        val prevCount = getArticleSize()
        val created = client.post().uri("/article").accept(APPLICATION_JSON)
            .bodyValue(ReqCreate(title = "title 1", body = "it is r2dbc demo", authorId = 1234))
            .exchange().expectBody(Article::class.java).returnResult().responseBody!!

        client.delete().uri("/article/${created.id}").accept(APPLICATION_JSON).exchange()

        val currentCount = getArticleSize()
        Assertions.assertThat(prevCount).isEqualTo(currentCount)
    }

    private fun getArticleSize(): Int {
        return client.get().uri("/article/all").accept(APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody(List::class.java)
            .returnResult().responseBody?.size ?: 0
    }

}