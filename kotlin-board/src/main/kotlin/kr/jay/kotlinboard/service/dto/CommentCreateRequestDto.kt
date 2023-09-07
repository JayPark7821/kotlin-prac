package kr.jay.kotlinboard.service.dto

import kr.jay.kotlinboard.domain.Comment
import kr.jay.kotlinboard.domain.Post

/**
 * CommentCreateRequestDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentCreateRequestDto(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post) =
    Comment(
        content = this.content,
        post = post,
        createdBy = this.createdBy,
        )
