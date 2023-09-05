package kr.jay.kotlinboard.controller

import kr.jay.kotlinboard.controller.dto.PostCreateRequest
import org.springframework.web.bind.annotation.*

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

}
