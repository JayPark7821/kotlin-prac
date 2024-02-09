package kr.jay.couponconsumer.component

import kr.jay.couponconsumer.TestConfig
import kr.jay.couponcore.repository.redis.RedisRepository
import kr.jay.couponcore.service.CouponIssueService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate

/**
 * CouponIssueListenerTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/9/24
 */
@Import(CouponIssueListener::class)
class CouponIssueListenerTest : TestConfig() {

    @Autowired
    private lateinit var sut: CouponIssueListener

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    private lateinit var redisRepository: RedisRepository

    @MockBean
    private lateinit var couponIssueService: CouponIssueService

    @BeforeEach
    fun clear(){
       val keys = redisTemplate.keys("*")
        redisTemplate.delete(keys)
    }

    @Test
    fun `쿠폰 발급 큐에 처리 대상이 없다면 발급을 하지 않는다`(){
        sut.issue()
        verify(couponIssueService, never()).issue(anyLong(), anyLong())
    }

    @Test
    fun `쿠폰 발급 큐에 처리 대상이 있다면 발급 한다`(){
        val couponId = 1L
        val userId = 1L
        val totalQuantity = Integer.MAX_VALUE
        redisRepository.issueRequest(couponId, userId, totalQuantity)

        sut.issue()

        verify(couponIssueService, times(1)).issue(anyLong(), anyLong())
    }

    @Test
    fun `쿠폰 발급 요청 순서에 맞게 처리한다`(){
        val couponId = 1L
        val userId1 = 1L
        val userId2 = 2L
        val userId3 = 3L
        val totalQuantity = Integer.MAX_VALUE
        redisRepository.issueRequest(couponId, userId1, totalQuantity)
        redisRepository.issueRequest(couponId, userId2, totalQuantity)
        redisRepository.issueRequest(couponId, userId3, totalQuantity)

        sut.issue()

        inOrder(couponIssueService).apply {
            verify(couponIssueService, times(1)).issue(couponId, userId1)
            verify(couponIssueService, times(1)).issue(couponId, userId2)
            verify(couponIssueService, times(1)).issue(couponId, userId3)
        }
    }


}