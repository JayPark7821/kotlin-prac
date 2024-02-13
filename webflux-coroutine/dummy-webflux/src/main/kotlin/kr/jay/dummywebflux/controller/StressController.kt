package kr.jay.dummywebflux.controller

import kotlinx.coroutines.delay
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * StressController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/13/24
 */
@RestController
class StressController {

    @GetMapping("/delay")
    suspend fun delayApi(): String {
        delay(5000L)
        return " delay 5 seconds"
    }

    @GetMapping("/test/circuit/child/{flag}", "/test/circuit/child", "/test/circuit/child/")
    suspend fun testCircuitBreaker(@PathVariable flag:String?): String {
        return if(flag?.lowercase() == "n"){
            throw RuntimeException("fail")
        }else{
            "success"
        }
    }
}