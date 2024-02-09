package kr.jay.couponcore.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.couponcore.TestConfig
import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.model.Coupon
import kr.jay.couponcore.model.CouponType
import kr.jay.couponcore.repository.mysql.CouponJpaRepository
import kr.jay.couponcore.repository.redis.dto.CouponIssueRequest
import kr.jay.couponcore.utils.CouponRedisUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime
import java.util.stream.IntStream

/**
 * AsyncCouponIssueServiceV2Test
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/8/24
 */
class AsyncCouponIssueServiceV2Test: TestConfig(){

    @Autowired
    lateinit var sut: AsyncCouponIssueServiceV2

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    lateinit var couponJpaRepository: CouponJpaRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        redisTemplate.keys("*").let { redisTemplate.delete(it) }
    }

    @Test
    fun `쿠폰 발급 - 쿠폰이 존재하지 않는다면 예외를 반환한다`() {
        val couponId: Long = 1
        val userId: Long = 1

        Assertions.assertThatThrownBy {
            sut.issue(couponId, userId)
        }.isInstanceOfSatisfying(CouponIssueException::class.java) { exception ->
            assertEquals(exception.errorCode, ErrorCode.COUPON_NOT_EXIST)
        }
    }

    @Test
    fun `쿠폰 발급 - 발급 가능 수량이 존재하지 않는다면 예외를 반환한다`() {
        val userId: Long = 1000
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 10)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }

        IntStream.range(0, coupon.totalQuantity!!.toInt()).forEach { idx->
            redisTemplate.opsForSet().add(CouponRedisUtils.getIssueRequestKey(coupon.id!!), idx.toString())
        }

        Assertions.assertThatThrownBy {
            sut.issue(coupon.id!!, userId)
        }.isInstanceOfSatisfying(CouponIssueException::class.java) { exception ->
            assertEquals(exception.errorCode, ErrorCode.INVALID_COUPON_ISSUE_QUANTITY)
        }
    }

    @Test
    fun `쿠폰 발급 - 이미 발급된 유저라면 예외를 반환한다`() {
        val userId: Long = 1
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 10)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }
        redisTemplate.opsForSet().add(CouponRedisUtils.getIssueRequestKey(coupon.id!!), userId.toString())

        Assertions.assertThatThrownBy {
            sut.issue(coupon.id!!, userId)
        }.isInstanceOfSatisfying(CouponIssueException::class.java) { exception ->
            assertEquals(exception.errorCode, ErrorCode.DUPLICATED_COUPON_ISSUE)
        }
    }

    @Test
    fun `쿠폰 발급 - 발급 기한이 유효하지 않다면 예외를 반환한다`() {
        val userId: Long = 1
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 10)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }
        redisTemplate.opsForSet().add(CouponRedisUtils.getIssueRequestKey(coupon.id!!), userId.toString())

        Assertions.assertThatThrownBy {
            sut.issue(coupon.id!!, userId)
        }.isInstanceOfSatisfying(CouponIssueException::class.java) { exception ->
            assertEquals(exception.errorCode, ErrorCode.INVALID_COUPON_ISSUE_DATE)
        }
    }

    @Test
    fun `쿠폰 발급 - 쿠폰 발급을 기록한다`() {
        val userId: Long = 1
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 10)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }

        sut.issue(coupon.id!!, userId)

        redisTemplate.opsForSet().isMember(CouponRedisUtils.getIssueRequestKey(coupon.id!!), userId.toString()).let {
            Assertions.assertThat(it).isTrue
        }
    }

    @Test
    fun `쿠폰 발급 - 쿠폰 발급 요청이 성공하면 쿠폰 발급 큐에 저장한다`() {
        val userId: Long = 1
        val coupon = Coupon(
            "선착순 쿠폰",
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(2),
            CouponType.FIRST_COME_FIRST_SERVED
        ).let {
            ReflectionTestUtils.setField(it, "totalQuantity", 10)
            ReflectionTestUtils.setField(it, "issuedQuantity", 0)
            couponJpaRepository.save(it)
        }

        sut.issue(coupon.id!!, userId)
        redisTemplate.opsForList().leftPop(CouponRedisUtils.getIssueRequestQueueKey()).let {
            Assertions.assertThat(it)
                .isEqualTo(objectMapper.writeValueAsString(CouponIssueRequest(coupon.id!!, userId)))
        }
    }
}