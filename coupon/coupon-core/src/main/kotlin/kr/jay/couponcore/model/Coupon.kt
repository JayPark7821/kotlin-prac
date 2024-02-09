package kr.jay.couponcore.model

import jakarta.persistence.*
import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
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
    title: String,
    dateIssueStart: LocalDateTime,
    dateIssueEnd: LocalDateTime,
    couponType: CouponType,
) : BaseTimeEntity() {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @Column(nullable = false)
    var title: String = title
        private set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var couponType: CouponType = couponType
        private set
    var totalQuantity: Int? = null
        private set

    @Column(nullable = false)
    var issuedQuantity: Int = 0
        private set

    var discountAmount: Int? = null
        private set

    var minAvailableAmount: Int? = null
        private set

    @Column(nullable = false)
    var dateIssueStart: LocalDateTime = dateIssueStart
        private set

    @Column(nullable = false)
    var dateIssueEnd: LocalDateTime = dateIssueEnd
        private set

    fun availableIssueQuantity(): Boolean {
        if (totalQuantity == null) {
            return true;
        }
        return totalQuantity!! > issuedQuantity
    }

    fun availableIssueDate(): Boolean {
        val now = LocalDateTime.now()
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now)
    }

    fun isIssueComplete() = dateIssueEnd.isBefore(LocalDateTime.now()) || !availableIssueQuantity()

    fun issue() {
        if (!availableIssueQuantity()) {
            throw CouponIssueException(
                ErrorCode.INVALID_COUPON_ISSUE_QUANTITY,
                "발급 가능한 수량을 초과했습니다. " +
                    "total : $totalQuantity, " +
                    "issued : $issuedQuantity"
            )
        }
        if (!availableIssueDate()) {
            throw CouponIssueException(
                ErrorCode.INVALID_COUPON_ISSUE_DATE,
                "발급 가능한 일자가 아닙니다. " +
                    "request : ${LocalDateTime.now()}, " +
                    "issueStart : $dateIssueStart, " +
                    "issueEnd : $dateIssueEnd"
            )
        }
        issuedQuantity = issuedQuantity.plus(1)
    }
}