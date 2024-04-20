package kr.jay.webfluxcoroutine.controller

import kotlinx.coroutines.flow.Flow
import kr.jay.webfluxcoroutine.model.Article
import kr.jay.webfluxcoroutine.service.ArticleService
import kr.jay.webfluxcoroutine.service.QryArticle
import kr.jay.webfluxcoroutine.service.ReqCreate
import kr.jay.webfluxcoroutine.service.ReqUpdate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * ArticleController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody request: ReqCreate): Article {
        return service.create(request)
    }

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long): Article {
        return service.get(id)
    }

    @GetMapping("/all")
    suspend fun getAll(@RequestParam title: String?): Flow<Article> {
        return service.getAll(title)
    }

    @GetMapping("/all/qry")
    suspend fun getAll(request: QryArticle): Flow<Article> {
        return service.getAll(request)
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody request: ReqUpdate): Article {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: Long) {
        return service.delete(id)
    }
}