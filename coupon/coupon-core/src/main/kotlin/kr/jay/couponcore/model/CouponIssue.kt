package kr.jay.couponcore.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

/**
 * CouponIssue
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */

@Entity
@Table(name = "coupon_issues")
class CouponIssue : BaseTimeEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var couponId: Long? = null

    @Column(nullable = false)
    var userId: Long? = null

    @Column(nullable = false)
    @CreatedDate
    var dateIssued: LocalDateTime? = null

    var dateUsed: LocalDateTime? = null

}