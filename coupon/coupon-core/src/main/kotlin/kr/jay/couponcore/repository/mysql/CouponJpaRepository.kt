package kr.jay.couponcore.repository.mysql

import jakarta.persistence.LockModeType
import kr.jay.couponcore.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

/**
 * CouponJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
interface CouponJpaRepository : JpaRepository<Coupon, Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    fun findCouponWithLock(couponId: Long): Coupon?
}
