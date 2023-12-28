package kr.jay.webfluxcoroutine.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * HelloController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */
@RestController
class HelloController {

    @GetMapping("/")
    suspend fun index(): String {
        return "main"
    }

    @GetMapping("/hello/{name}")
    suspend fun hello(@PathVariable name: String?): String {
        return "hello $name"
    }
}