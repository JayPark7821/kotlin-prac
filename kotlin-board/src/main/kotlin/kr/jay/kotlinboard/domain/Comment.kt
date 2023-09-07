package kr.jay.kotlinboard.domain

import jakarta.persistence.*
import kr.jay.kotlinboard.exception.CommentNotUpdatableException
import kr.jay.kotlinboard.service.dto.CommentUpdateRequestDto

/**
 * Comment
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
@Entity
class Comment(
    content: String,
    post: Post,
    createdBy: String,
): BaseEntity(createdBy) {
    fun update(updateRequestDto: CommentUpdateRequestDto) {
        if(updateRequestDto.updatedBy != this.createdBy)
            throw CommentNotUpdatableException()
        this.content = updateRequestDto.content
        super.updatedBy(updateRequestDto.updatedBy)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        protected set
}
