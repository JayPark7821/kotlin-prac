package kr.jay.couponapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * HellController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */

@RestController
class HellController {

    @GetMapping("/hello")
    fun hello() = "Hello, World!"
//    server.tomcat.threads.max: 200
}