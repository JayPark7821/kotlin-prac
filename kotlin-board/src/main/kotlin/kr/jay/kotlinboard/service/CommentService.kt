package kr.jay.kotlinboard.service

import kr.jay.kotlinboard.exception.CommentNotDeletableException
import kr.jay.kotlinboard.exception.CommentNotFoundException
import kr.jay.kotlinboard.exception.PostNotFoundException
import kr.jay.kotlinboard.repository.CommentRepository
import kr.jay.kotlinboard.repository.PostRepository
import kr.jay.kotlinboard.service.dto.CommentCreateRequestDto
import kr.jay.kotlinboard.service.dto.CommentUpdateRequestDto
import kr.jay.kotlinboard.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * CommentService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
@Service
@Transactional(readOnly = true)
class CommentService (
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
){

    @Transactional
    fun createComment(
        postId: Long,
        createRequestDto: CommentCreateRequestDto
    ): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save(createRequestDto.toEntity(post)).id
    }

    @Transactional
    fun updateComment(
        commentId: Long,
        updateRequestDto: CommentUpdateRequestDto
    ): Long {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        comment.update( updateRequestDto)
        return comment.id
    }

    @Transactional
    fun deleteComment(commentId: Long, deletedBy: String): Long {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        if(comment.createdBy != deletedBy)
            throw CommentNotDeletableException()
        commentRepository.delete(comment)
        return commentId
    }
}
