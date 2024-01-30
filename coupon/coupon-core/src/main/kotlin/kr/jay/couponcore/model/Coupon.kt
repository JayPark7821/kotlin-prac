package kr.jay.couponcore.model

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Coupon
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */

@Entity
@Table(name = "coupons")
class Coupon(
   dateIssueStart: LocalDateTime,
    dateIssueEnd: LocalDateTime,
) : BaseTimeEntity() {

    private val dateIssueEnd = dateIssueEnd

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var couponType: CouponType? = null

    var totalQuantity: Int? = null

    @Column(nullable = false)
    var issuedQuantity: Int = 0

    @Column(nullable = false)
    var discountAmount: Int? = null

    @Column(nullable = false)
    var minAvailableAmount: Int? = null

    @Column(nullable = false)
    var dateIssuedStart: LocalDateTime = dateIssueStart

    @Column(nullable = false)
    var dateIssuedEnd: LocalDateTime = dateIssueEnd

    private fun availableIssueQuantity(): Boolean {
        if (totalQuantity == null) {
            return true;
        }
        return totalQuantity!! > issuedQuantity
    }

    private fun availableIssueDate(): Boolean{
        val now = LocalDateTime.now()
        return dateIssuedStart.isBefore(now) && dateIssuedEnd.isAfter(now)
    }

    fun issue() {
        if(!availableIssueQuantity()) {
            throw RuntimeException("쿠폰이 모두 소진되었습니다.")
        }
        if (!availableIssueDate()) {
            throw RuntimeException("쿠폰이 발급 기간이 아닙니다.")
        }
        issuedQuantity = issuedQuantity.plus(1)
    }
}