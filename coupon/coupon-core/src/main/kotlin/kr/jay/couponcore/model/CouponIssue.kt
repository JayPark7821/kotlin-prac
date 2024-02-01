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
class CouponIssue(
    @Column(nullable = false) val couponId: Long,
    @Column(nullable = false) val userId: Long,
) : BaseTimeEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    @CreatedDate
    var dateIssued: LocalDateTime = LocalDateTime.now()

    var dateUsed: LocalDateTime? = null

}