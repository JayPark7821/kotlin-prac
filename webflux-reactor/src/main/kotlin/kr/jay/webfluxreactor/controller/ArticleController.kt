package kr.jay.webfluxreactor.controller

import kr.jay.webfluxreactor.model.Article
import kr.jay.webfluxreactor.service.ArticleService
import kr.jay.webfluxreactor.service.ReqCreate
import kr.jay.webfluxreactor.service.ReqUpdate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * ArticleController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request:ReqCreate): Mono<Article> {
        return service.create(request)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<Article> {
        return service.get(id)
    }

    @GetMapping("/all")
    fun getAll(@RequestParam title: String?): Flux<Article> {
        return service.getAll(title)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: ReqUpdate
    ): Mono<Article> {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        return service.delete(id)
    }
}