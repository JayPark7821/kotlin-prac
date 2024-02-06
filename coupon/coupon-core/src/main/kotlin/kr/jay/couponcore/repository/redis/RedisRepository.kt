package kr.jay.couponcore.repository.redis

import org.springframework.data.redis.core.RedisTemplate
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
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun sAdd(key: String, value: String) =
        redisTemplate.opsForSet().add(key, value) ?: throw RuntimeException("Failed to add value to set")

    fun sCard(key: String) =
        redisTemplate.opsForSet().size(key) ?: throw RuntimeException("Failed to get set size")

    fun sIsMember(key: String, value: String) =
        redisTemplate.opsForSet().isMember(key, value) ?: throw RuntimeException("Failed to check if value is member of set")

    fun rPush(key: String, value: String) =
        redisTemplate.opsForList().rightPush(key, value) ?: throw RuntimeException("Failed to add value to list")
}