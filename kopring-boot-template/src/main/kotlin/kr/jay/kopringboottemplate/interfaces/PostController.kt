package kr.jay.kopringboottemplate.interfaces

import kr.jay.kopringboottemplate.domain.Post
import kr.jay.kopringboottemplate.domain.service.PostService
import org.springframework.web.bind.annotation.*

/**
 * PostController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody request: PostRequest) =
        PostResponse(postService.createPost(request.userId, request.title, request.content))

    @GetMapping
    fun findAllPosts()= postService.findAllPosts().map { PostResponse(it) }
}

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val userName : String,
){
    constructor(post: Post):
    this(
        id = post.id,
        title = post.title,
        content = post.content,
        userName = post.users.name,
    )
}

data class PostRequest(
    val userId: Long,
    val title: String,
    val content: String,
)