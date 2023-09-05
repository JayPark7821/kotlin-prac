package kr.jay.kotlinboard.service

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.context.SpringBootTest

/**
 * PostServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */

@SpringBootTest
class PostServiceTest (
    private val postService: PostService,
    private val postRepository: PostRepository,
): BehaviorSpec({
    given("게시글 생성시"){
        When("게시글 생성"){
            val postId = postService.createPost(PostCreateRequestDto(
                title = "제목",
                content = "내용",
                createdBy = "작성자"
            ))
            then("게시글이 정상적으로 생성된다."){
                postId shouldBeGreaterThen 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
            }
        }
    }
}){

}
