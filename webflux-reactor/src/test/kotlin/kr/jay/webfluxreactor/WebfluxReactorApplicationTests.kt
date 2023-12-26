package kr.jay.webfluxreactor

import kr.jay.webfluxreactor.model.Article
import kr.jay.webfluxreactor.repository.ArticleRepository
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private val logger = KotlinLogging.logger{}
@SpringBootTest
class WebfluxReactorApplicationTests(
	@Autowired private val repository: ArticleRepository
) {

	@Test
	fun contextLoads() {
		val preCount = repository.count().block() ?: 0
		repository.save(Article(title = "test", body = "test", authorId = 1)).block()
		val articles = repository.findAll().collectList().block()
		articles?.forEach{
			logger.debug { " >> article -> $it" }
		}

		val currentCount = repository.count().block() ?: 0
		Assertions.assertThat( preCount + 1L).isEqualTo(currentCount)

	}

}
