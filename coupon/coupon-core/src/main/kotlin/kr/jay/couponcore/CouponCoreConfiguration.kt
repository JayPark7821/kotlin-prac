package kr.jay.couponcore

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

/**
 * CouponCoreConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */

@EnableJpaAuditing
@ComponentScan
@EnableAutoConfiguration
class CouponCoreConfiguration