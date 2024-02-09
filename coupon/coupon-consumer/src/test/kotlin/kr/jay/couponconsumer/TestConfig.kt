package kr.jay.couponconsumer

import kr.jay.couponcore.CouponCoreConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional

/**
 * TestConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */

@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = ["spring.config.name=application-core"])
@SpringBootTest(classes = [CouponCoreConfiguration::class])
class TestConfig {
}