package kr.jay.couponcore.repository.mysql

import kr.jay.couponcore.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository

/**
 * CouponJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
interface CouponJpaRepository : JpaRepository<Coupon, Long>
