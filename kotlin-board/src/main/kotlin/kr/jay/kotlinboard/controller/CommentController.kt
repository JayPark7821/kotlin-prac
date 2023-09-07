package kr.jay.kotlinboard.controller

import kr.jay.kotlinboard.controller.dto.CommentCreateRequest
import kr.jay.kotlinboard.controller.dto.CommentUpdateRequest
import kr.jay.kotlinboard.controller.dto.toDto
import kr.jay.kotlinboard.service.CommentService
import org.springframework.web.bind.annotation.*

/**
 * CommentController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */

@RestController
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest
    ): Long {
        return commentService.createComment(postId, commentCreateRequest.toDto())
    }

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest
    ) : Long {
        return commentService.updateComment(commentId, commentUpdateRequest.toDto())
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String
    ) : Long {
        return commentService.deleteComment(commentId, deletedBy)
    }
}

