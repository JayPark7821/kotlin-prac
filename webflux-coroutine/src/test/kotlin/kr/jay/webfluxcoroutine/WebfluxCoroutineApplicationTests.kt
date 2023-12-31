package kr.jay.webfluxcoroutine

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import kr.jay.webfluxcoroutine.model.Article
import kr.jay.webfluxcoroutine.repository.ArticleRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class WebfluxCoroutineApplicationTests(
	@Autowired private val repository : ArticleRepository
): StringSpec({
	"context load".config(enabled = false) {
		val prevCount = repository.count()
		repository.save(Article(title="test", body="test", authorId=1))
		val currentCount = repository.count()
		currentCount shouldBe prevCount + 1
//		Assertions.assertThat(currentCount).isEqualTo(prevCount + 1)
	}
}){
//	@Test
//	fun contextLoads() {
//		runBlocking {
//			val prevCount = repository.count()
//			repository.save(Article(title="test", body="test", authorId=1))
//			val currentCount = repository.count()
//			Assertions.assertThat(currentCount).isEqualTo(prevCount + 1)
//		}
//	}
}
