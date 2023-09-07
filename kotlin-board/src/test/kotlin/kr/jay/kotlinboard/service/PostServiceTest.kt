package kr.jay.kotlinboard.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import kr.jay.kotlinboard.domain.Comment
import kr.jay.kotlinboard.domain.Post
import kr.jay.kotlinboard.exception.PostNotDeletableException
import kr.jay.kotlinboard.exception.PostNotFoundException
import kr.jay.kotlinboard.exception.PostNotUpdatableException
import kr.jay.kotlinboard.repository.CommentRepository
import kr.jay.kotlinboard.repository.PostRepository
import kr.jay.kotlinboard.service.dto.PostCreateRequestDto
import kr.jay.kotlinboard.service.dto.PostSearchRequestDto
import kr.jay.kotlinboard.service.dto.PostUpdateRequestDto
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

/**
 * PostServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : BehaviorSpec({
    beforeSpec {
        postRepository.saveAll(
            listOf(
                Post(title = "title1", content = "content1", createdBy = "jay"),
                Post(title = "title12", content = "content2", createdBy = "jay1"),
                Post(title = "title13", content = "content3", createdBy = "jay1"),
                Post(title = "title14", content = "content4", createdBy = "jay1"),
                Post(title = "title15", content = "content5", createdBy = "jay"),
                Post(title = "title6", content = "content6", createdBy = "jay2"),
                Post(title = "title7", content = "content7", createdBy = "jay2"),
                Post(title = "title8", content = "content8", createdBy = "jay2"),
                Post(title = "title9", content = "content9", createdBy = "jay"),
                Post(title = "title10", content = "content10", createdBy = "jay")
            )
        )
    }
    given("게시글 생성시") {
        When("게시글 요청이 정상적으로 들어오면") {
            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자"
                )
            )
            then("게시글이 정상적으로 생성된다.") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
                post?.title shouldBe "제목"
                post?.content shouldBe "내용"
                post?.createdBy shouldBe "작성자"
            }
        }
    }
    given("게시글 수정시") {
        val savedPost = postRepository.save(Post(title = "title", content = "content", createdBy = "jay"))
        When("정상 수정시") {
            val updatedId = postService.updatePost(
                savedPost.id,
                PostUpdateRequestDto(title = "newTitle", content = "newContent", updatedBy = "jay")
            )
            then("게시글이 정상적으로 수정된다.") {
                savedPost.id shouldBeEqual updatedId
                val updatedPost = postRepository.findByIdOrNull(updatedId)
                updatedPost shouldNotBe null
                updatedPost?.title shouldBe "newTitle"
                updatedPost?.content shouldBe "newContent"
            }
        }
        When("게시글이 없을 떄") {
            then("게시글을 찾을수 없다는 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    postService.updatePost(
                        9999999L,
                        PostUpdateRequestDto(title = "newTitle", content = "newContent", updatedBy = "jay")
                    )
                }
            }
        }
        When("작성자가 동일하지 않으면") {
            then("수정할 수 없는 게시물 입니다 예외가 발생한다.") {
                shouldThrow<PostNotUpdatableException> {
                    postService.updatePost(
                        1L,
                        PostUpdateRequestDto(title = "newTitle", content = "newContent", updatedBy = "kay")
                    )
                }
            }
        }
    }
    given("게시글 삭제시") {
        val savedPost = postRepository.save(Post(title = "title", content = "content", createdBy = "jay"))
        When("정상 삭제시") {
            val deletedPostId = postService.deletePost(savedPost.id, "jay")
            then("게시글이 정상적으로 삭제된다.") {
                deletedPostId shouldBeEqual savedPost.id
                postRepository.findByIdOrNull(deletedPostId) shouldBe null
            }
        }
        When("작성자가 동일하지 않으면") {
            val savedPost2 = postRepository.save(Post(title = "title", content = "content", createdBy = "jay"))
            then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                shouldThrow<PostNotDeletableException> {
                    postService.deletePost(savedPost2.id, "kay")
                }
            }
        }
    }
    given("게시글 상세조회시") {
        val savedPost = postRepository.save(Post(title = "title", content = "content", createdBy = "jay"))
        When("정상 조회시") {
            val post = postService.getPost(savedPost.id)
            then("게시글의 내용이 정상적으로 반환된다.") {
                post.title shouldBe "title"
                post.content shouldBe "content"
                post.createdBy shouldBe "jay"
            }
        }
        When("게시글이 없을 때") {
            then("게시글을 찾을수 없다는 예외 발생") {
                shouldThrow<PostNotFoundException> {
                    postService.getPost(9999999L)
                }
            }
        }
        When("댓글 추가시"){
            val savedComment1 = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
            val savedComment2 = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
            val savedComment3 = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
            val savedPost = postService.getPost(savedPost.id)
            then("댓글이 함께 조회됨"){
                savedPost.comments.size shouldBe 3
                savedPost.comments[0].content shouldBe "댓글 내용"
                savedPost.comments[0].createdBy shouldBe "jay"
            }
        }
    }
    given("게시글 목록조회시") {
        When("정상 조회시") {
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
            then("게시글 목록이 정상적으로 반환된다.") {
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title"
                postPage.content[0].createdBy shouldBe "jay"
            }
        }
        When("타이틀 검색") {
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(title = "title1"))
            then("타이틀에 해당하는 게시글 반환") {
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title1"
                postPage.content[0].createdBy shouldContain "jay"
            }
        }
        When("작성자로 검색") {
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "jay1"))
            then("작성자에 해당하는 게시글이 반환") {
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 3
                postPage.content[0].title shouldContain "title"
                postPage.content[0].createdBy shouldBe "jay1"
            }
        }
    }
})
