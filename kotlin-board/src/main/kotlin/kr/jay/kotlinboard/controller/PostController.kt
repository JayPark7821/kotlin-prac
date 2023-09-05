package kr.jay.kotlinboard.controller

import kr.jay.kotlinboard.controller.dto.PostCreateRequest
import kr.jay.kotlinboard.controller.dto.PostDetailResponse
import kr.jay.kotlinboard.controller.dto.PostSearchRequest
import kr.jay.kotlinboard.controller.dto.PostSummaryResponse
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import java.awt.print.Pageable
import java.time.LocalDateTime

/**
 * PostController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */

@RestController
class PostController {

    @PostMapping("/post")
    fun createPost(
        @RequestBody request: PostCreateRequest
    ): Long{
        return 1L
    }

    @PutMapping("/post/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody request: PostCreateRequest,
    ): Long{
        return 1L
    }

    @DeleteMapping("/post/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @RequestParam createdBy: String,
    ): Long{
        return id
    }

    @GetMapping("/post/{id}")
    fun getPost(
        @PathVariable id: Long,
    ): PostDetailResponse{
        return PostDetailResponse(
            id = id,
            title = "title",
            content = "content",
            createdBy = "createdBy",
            createdAt = LocalDateTime.now().toString(),
        )
    }

    @GetMapping("/posts")
    fun getPosts(
        pageable: Pageable,
        postSearchRequest: PostSearchRequest,
    ): Page<PostSummaryResponse> {
        return Page.empty()
    }
}
