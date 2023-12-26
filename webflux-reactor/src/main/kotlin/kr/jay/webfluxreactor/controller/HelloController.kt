package kr.jay.webfluxreactor.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * HelloController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/25/23
 */

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(@RequestParam name:String): Mono<String> = Mono.just("Hello, $name!")

    @GetMapping("/")
    fun index():String{
        return "main"
    }
}