package kr.jay.couponcore.service

import kr.jay.couponcore.TestConfig
import kr.jay.couponcore.utils.CouponRedisUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import java.util.stream.IntStream

/**
 * CouponIssueRedisServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */
class CouponIssueRedisServiceTest : TestConfig() {

    @Autowired
    lateinit var sut: CouponIssueRedisService

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @BeforeEach
    fun setUp() {
        redisTemplate.keys("*").let { redisTemplate.delete(it) }
    }

    @Test
    fun `쿠폰 수량 검증 - 발급 가능 수량이 존재하면 true를 반환한다`() {
        val totalIssueQuantity: Int = 10
        val couponId: Long = 1

        val result = sut.availableTotalIssueQuantity(totalIssueQuantity, couponId)

        Assertions.assertThat(result).isTrue
    }

    @Test
    fun `쿠폰 수량 검증 - 발급 가능 수량이 모두 소진되면 false를 반환한다`() {
        val totalIssueQuantity: Int = 10
        val couponId: Long = 1
        IntStream.range(0, totalIssueQuantity).forEach { userId->
            redisTemplate.opsForSet().add(CouponRedisUtils.getIssueRequestKey(couponId), userId.toString())
        }

        val result = sut.availableTotalIssueQuantity(totalIssueQuantity, couponId)

        Assertions.assertThat(result).isFalse()
    }

    @Test
    fun `쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하지 않으면 true를 반환한다`() {
        val couponId: Long = 1
        val userId: Long = 1

        val result = sut.availableUserIssueQuantity(couponId, userId)

        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun `쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하면 false를 반환한다`() {
        val couponId: Long = 1
        val userId: Long = 1
        redisTemplate.opsForSet().add(CouponRedisUtils.getIssueRequestKey(couponId), userId.toString())

        val result = sut.availableUserIssueQuantity(couponId, userId)

        Assertions.assertThat(result).isFalse()
    }





}