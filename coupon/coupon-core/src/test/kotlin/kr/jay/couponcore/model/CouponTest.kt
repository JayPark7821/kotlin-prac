package kr.jay.couponcore.model

import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

/**
 * CouponTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */
class CouponTest {

    @Test
    @DisplayName("발급 수량이 남아있다면 true를 반환한다.")
    fun availableIssuedQuantity_1(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now(),
            LocalDateTime.now(),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "totalQuantity", 100)
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 99)

        val result = coupon.availableIssueQuantity()

        assertThat(result).isTrue()
    }

    @Test
    @DisplayName("발급 수량이 소진되었다면 false를 반환한다.")
    fun availableIssuedQuantity_2(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now(),
            LocalDateTime.now(),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "totalQuantity", 100)
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 100)

        val result = coupon.availableIssueQuantity()

        assertThat(result).isFalse()
    }

    @Test
    @DisplayName("최대 발급 수량이 설정되어있지 않다면 true를 반환한다.")
    fun availableIssuedQuantity_3(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now(),
            LocalDateTime.now(),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 100)

        val result = coupon.availableIssueQuantity()

        assertThat(result).isTrue()
    }

    @Test
    @DisplayName("발급 기간에 해당되면 true 반환한다.")
    fun availableIssuedDate_2(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        )

        val result = coupon.availableIssueDate()

        assertThat(result).isTrue()
    }

    @Test
    @DisplayName("발급 기간이 종료되면 false를 반환한다.")
    fun availableIssuedDate_3(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(3),
            LocalDateTime.now().minusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        )

        val result = coupon.availableIssueDate()

        assertThat(result).isFalse()
    }

    @Test
    @DisplayName("발급 기간이 시작되지 않았다면 false를 반환한다.")
    fun availableIssuedDate_1(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        )

        val result = coupon.availableIssueDate()

        assertThat(result).isFalse()
    }

    @Test
    @DisplayName("발급 수량과 발급 기간이 유효하다면 발급에 성공한다.")
    fun issue_1(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "totalQuantity", 100)
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 99)


        coupon.issue()

        assertThat(coupon.issuedQuantity).isEqualTo(100)
    }

    @Test
    @DisplayName("발급 수량을 초과하면 예외를 반환한다.")
    fun issue_2(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "totalQuantity", 100)
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 100)

        assertThatThrownBy { coupon.issue() }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY)
            }
    }

    @Test
    @DisplayName("발급 기간이 아니면 예외를 반환한다.")
    fun issue_3(){
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().plusDays(3),
            LocalDateTime.now().plusDays(5),
            CouponType.FIRST_COME_FIRST_SERVED
        )
        ReflectionTestUtils.setField(coupon, "totalQuantity", 100)
        ReflectionTestUtils.setField(coupon, "issuedQuantity", 99)

        assertThatThrownBy { coupon.issue() }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_DATE)
            }
    }
}