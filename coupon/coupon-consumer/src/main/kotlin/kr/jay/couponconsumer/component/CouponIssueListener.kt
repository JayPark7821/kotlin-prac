package kr.jay.couponconsumer.component

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.couponcore.repository.redis.RedisRepository
import kr.jay.couponcore.repository.redis.dto.CouponIssueRequest
import kr.jay.couponcore.service.CouponIssueService
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestKey
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestQueueKey
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * CouponIssueListener
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/9/24
 */
@EnableScheduling
@Component
class CouponIssueListener(
    private val redisRepository: RedisRepository,
    private val couponIssueService: CouponIssueService,
    private val objectMapper: ObjectMapper
) {

    @Scheduled(fixedDelay = 1000L)
    fun issue(){
        val issueRequestQueueKey = getIssueRequestQueueKey()
        while(existCouponIssueTarget(issueRequestQueueKey)){
            val target: CouponIssueRequest = getIssueTarget(issueRequestQueueKey)
            couponIssueService.issue(target.couponId, target.userId)
            removeIssuedTarget(issueRequestQueueKey)
        }
    }

    private fun removeIssuedTarget(key: String) =
        redisRepository.lPop(key)


    private fun getIssueTarget(key: String)=
        objectMapper.readValue(redisRepository.lIndex(key, 0), CouponIssueRequest::class.java)

    private fun existCouponIssueTarget(key: String) =
        redisRepository.lSize(key) > 0
}