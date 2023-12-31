package kr.jay.webfluxcoroutine.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kr.jay.webfluxcoroutine.model.Article
import kr.jay.webfluxcoroutine.repository.ArticleRepository
import kr.jay.webfluxcoroutine.service.ArticleService
import kr.jay.webfluxcoroutine.service.ReqCreate
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.temporal.ChronoUnit

private val logger = KotlinLogging.logger {}
@SpringBootTest
@ActiveProfiles("test")
class ArticleControllerTest(
    @Autowired private val repository: ArticleRepository,
    @Autowired private val service: ArticleService,
    @Autowired private val context: ApplicationContext,
) : StringSpec({
    val client = WebTestClient.bindToApplicationContext(context).build()

    fun getSize(title: String? = null):Int{
        return client.get().uri("/article/all${title?.let{"?title=$it"} ?: ""}").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody(List::class.java)
            .returnResult().responseBody?.size ?: 0
    }

    beforeTest {
        repository.deleteAll()
    }

    "create" {
        val request = ReqCreate(title = "test", body = "test", authorId = 1)
        client.post().uri("/article").accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("title").isEqualTo(request.title)
            .jsonPath("body").isEqualTo(request.body)
            .jsonPath("authorId").isEqualTo(request.authorId)
    }

    "get"{
        val created = client.post().uri("/article").accept(MediaType.APPLICATION_JSON)
            .bodyValue(ReqCreate(title = "test", body = "test", authorId = 1))
            .exchange()
            .expectStatus().isCreated
            .expectBody(Article::class.java)
            .returnResult().responseBody!!

        val read = client.get().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(Article::class.java)
            .returnResult().responseBody!!

        read.id shouldBe created.id
        read.title shouldBe created.title
        read.body shouldBe created.body
        read.authorId shouldBe created.authorId
        read.createdAt?.truncatedTo(ChronoUnit.SECONDS) shouldBe created.createdAt?.truncatedTo(ChronoUnit.SECONDS)
        read.updatedAt?.truncatedTo(ChronoUnit.SECONDS) shouldBe created.updatedAt?.truncatedTo(ChronoUnit.SECONDS)
    }

    "get all"{
        service.create(ReqCreate(title = "test1", body = "test", authorId = 1))
        service.create(ReqCreate(title = "test2", body = "test", authorId = 1))
        service.create(ReqCreate(title = "test matched", body = "test", authorId = 1))

        getSize() shouldBe 3
        getSize("matched") shouldBe 1
    }

    "update"{
        val created = service.create(ReqCreate(title = "test1", body = "test", authorId = 1))
        client.put().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .bodyValue(ReqCreate(title = "updated", body = "updated", authorId = 2))
            .exchange()

        client.get().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody()
            .jsonPath("title").isEqualTo("updated")
            .jsonPath("body").isEqualTo("updated")
            .jsonPath("authorId").isEqualTo(2)
    }

    "delete" {
        val created = service.create(ReqCreate(title = "test1", body = "test", authorId = 1))
        val prevCount = repository.count()

        client.delete().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .exchange()

        repository.count() shouldBe prevCount - 1
        repository.existsById(created.id) shouldBe false
    }
})
