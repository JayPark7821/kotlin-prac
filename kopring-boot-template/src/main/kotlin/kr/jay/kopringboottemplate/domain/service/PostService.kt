package kr.jay.kopringboottemplate.domain.service

import kr.jay.kopringboottemplate.domain.Post
import kr.jay.kopringboottemplate.infrastructure.PostJpaRepository
import kr.jay.kopringboottemplate.infrastructure.UserJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PostService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@Service
class PostService(
    private val postJpaRepository: PostJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val userService: UserService
) {
    fun getPostByUserId(id: Long): List<Post> {
        val user = userJpaRepository.findById(id).orElseThrow()
        return user.posts

    }



    @Transactional
    fun createPost(userId: Long, title: String, content: String): Post {
        val user = userService.getByUserId(1L)
        return postJpaRepository.save(Post(title, content, user))
    }
}