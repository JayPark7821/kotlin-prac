package kr.jay.couponapi

import kr.jay.couponcore.CouponCoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(CouponCoreConfiguration::class)
@SpringBootApplication
class CouponApiApplication

fun main(args: Array<String>) {

    System.setProperty("spring.config.name", "application-core,application-consumer")
    runApplication<CouponApiApplication>(*args)
}
