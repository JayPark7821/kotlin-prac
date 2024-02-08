package kr.jay.couponcore.repository.redis

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.couponcore.repository.redis.dto.CouponIssueRequest
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestKey
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestQueueKey
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Repository

/**
 * RedisRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {

    fun sAdd(key: String, value: String) =
        redisTemplate.opsForSet().add(key, value) ?: throw RuntimeException("Failed to add value to set")

    fun sCard(key: String) =
        redisTemplate.opsForSet().size(key) ?: throw RuntimeException("Failed to get set size")

    fun sIsMember(key: String, value: String) =
        redisTemplate.opsForSet().isMember(key, value)
            ?: throw RuntimeException("Failed to check if value is member of set")

    fun rPush(key: String, value: String) =
        redisTemplate.opsForList().rightPush(key, value) ?: throw RuntimeException("Failed to add value to list")

    fun issueRequest(
        couponId: Long,
        userId: Long,
        totalIssueQuantity: Int
    ) {
        val issueRequestKey = getIssueRequestKey(couponId)
        val couponIssueRequest = CouponIssueRequest(couponId, userId)

        val result = redisTemplate.execute(
            issueRequestScript(),
            listOf(issueRequestKey, getIssueRequestQueueKey()),
            userId.toString(),
            totalIssueQuantity.toString(),
            objectMapper.writeValueAsString(couponIssueRequest)
        )
        CouponIssueRequestCode.checkRequestResult(CouponIssueRequestCode.find(result))
    }

    private fun issueRequestScript(): RedisScript<String> {
        return RedisScript.of(
            """ 
            if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                return '2'
            end
            
            if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                redis.call('SADD', KEYS[1], ARGV[1])
                redis.call('RPUSH', KEYS[2], ARGV[3])
                return '1'
            end
            
            return '3'
        """
        )

    }
}