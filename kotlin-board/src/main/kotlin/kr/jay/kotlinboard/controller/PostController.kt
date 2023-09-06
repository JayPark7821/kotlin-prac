package kr.jay.kotlinboard.controller

import kr.jay.kotlinboard.controller.dto.PostCreateRequest
import kr.jay.kotlinboard.controller.dto.PostDetailResponse
import kr.jay.kotlinboard.controller.dto.PostSearchRequest
import kr.jay.kotlinboard.controller.dto.PostSummaryResponse
import kr.jay.kotlinboard.controller.dto.PostUpdateRequest
import kr.jay.kotlinboard.controller.dto.toDto
import kr.jay.kotlinboard.controller.dto.toResponse
import kr.jay.kotlinboard.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

/**
 * PostController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */

@RestController
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/post")
    fun createPost(
        @RequestBody request: PostCreateRequest,
    ): Long {
        return postService.createPost(request.toDto())
    }

    @PutMapping("/post/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody request: PostUpdateRequest,
    ): Long {
        return postService.updatePost(id, request.toDto())
    }

    @DeleteMapping("/post/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @RequestParam createdBy: String,
    ): Long {
        return postService.deletePost(id, createdBy)
    }

    @GetMapping("/post/{id}")
    fun getPost(
        @PathVariable id: Long,
    ): PostDetailResponse {
        return postService.getPost(id).toResponse()
    }

    @GetMapping("/posts")
    fun getPosts(
        pageable: Pageable,
        postSearchRequest: PostSearchRequest,
    ): Page<PostSummaryResponse> {
        return postService.findPageBy(pageable, postSearchRequest.toDto())
            .map { it.toResponse() }
    }
}
