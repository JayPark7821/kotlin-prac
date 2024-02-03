package kr.jay.couponcore.service

import kr.jay.couponcore.TestConfig
import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.model.Coupon
import kr.jay.couponcore.model.CouponIssue
import kr.jay.couponcore.model.CouponType
import kr.jay.couponcore.repository.mysql.CouponIssueJpaRepository
import kr.jay.couponcore.repository.mysql.CouponIssueRepository
import kr.jay.couponcore.repository.mysql.CouponJpaRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

/**
 * CouponIssueServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
class CouponIssueServiceTest : TestConfig() {
    @Autowired
    lateinit var sut: CouponIssueService

    @Autowired
    lateinit var couponIssueJpaRepository: CouponIssueJpaRepository

    @Autowired
    lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    lateinit var couponJpaRepository: CouponJpaRepository

    @BeforeEach
    fun setUp() {
        couponIssueJpaRepository.deleteAllInBatch()
        couponJpaRepository.deleteAllInBatch()
    }

    @Test
    fun `쿠폰 발급 내역이 존재하면 예외를 반환한다`() {
        val couponIssue = CouponIssue(1, 1)
            .let { couponIssueJpaRepository.save(it) }

        assertThatThrownBy {
            sut.saveCouponIssue(couponIssue.couponId, couponIssue.userId)
        }.isInstanceOfSatisfying(CouponIssueException::class.java) { exception ->
            assertEquals(exception.errorCode, ErrorCode.DUPLICATED_COUPON_ISSUE)
        }
    }

    @Test
    fun `쿠폰 발급 내역이 존재하지 않는다면 쿠폰을 발급한다`() {
        val couponId = 1L
        val userId = 1L

        val result = sut.saveCouponIssue(couponId, userId)

        assertThat(couponIssueJpaRepository.findById(result.id!!)).isPresent
    }

    @Test
    fun `발급 수량, 기한, 중복 발급 문제가 없다면 쿠폰을 발급한다`() {
        val userId = 1L
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 100)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }

        val result = sut.issue(coupon.id!!, userId)

        couponJpaRepository.findById(coupon.id!!).let { savedCoupon ->
            assertThat(savedCoupon.get().issuedQuantity).isEqualTo(1)
        }
        couponIssueRepository.findFirstCouponIssued(coupon.id!!, userId).let { savedCouponIssue ->
            assertThat(savedCouponIssue).isNotNull
        }
    }

    @Test
    fun `발급 수량에 문제가 있다면 예외를 반환한다`() {
        val userId = 1L
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 100)
            ReflectionTestUtils.setField(it, "issuedQuantity", 100)
            couponJpaRepository.save(it)
        }

        assertThatThrownBy {  sut.issue(coupon.id!!, userId) }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY)
            }
    }

    @Test
    fun `발급 기한에 문제가 있다면 예외를 반환한다`() {
        val userId = 1L
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now().minusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 100)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }

        assertThatThrownBy {  sut.issue(coupon.id!!, userId) }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_DATE)
            }
    }

    @Test
    fun `중복 발급 검증에 문제가 있다면 예외를 반환한다`() {
        val userId = 1L
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now().plusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 100)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }
        val couponIssue = CouponIssue(coupon.id!!, userId)
            .let { couponIssueJpaRepository.save(it) }


        assertThatThrownBy {  sut.issue(coupon.id!!, userId) }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.DUPLICATED_COUPON_ISSUE)
            }
    }

    @Test
    fun `쿠폰이 존재하지 않는다면 예외를 반환한다`() {
        val userId = 1L
        val couponId = 1L

        assertThatThrownBy {  sut.issue(couponId, userId) }
            .isInstanceOfSatisfying(CouponIssueException::class.java){ exception ->
                assertThat(exception.errorCode).isEqualTo(ErrorCode.COUPON_NOT_EXIST)
            }
    }

}