package kr.jay.kotlinboard.service

import kr.jay.kotlinboard.repository.PostRepository
import kr.jay.kotlinboard.service.dto.PostCreateRequestDto
import kr.jay.kotlinboard.service.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PostService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {

    @Transactional
    fun createPost(postCreateRequestDto: PostCreateRequestDto): Long {
        return postRepository.save(postCreateRequestDto.toEntity()).id
    }
}
