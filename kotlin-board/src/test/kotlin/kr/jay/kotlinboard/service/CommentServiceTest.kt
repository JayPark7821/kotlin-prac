package kr.jay.kotlinboard.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.jay.kotlinboard.domain.Comment
import kr.jay.kotlinboard.domain.Post
import kr.jay.kotlinboard.exception.CommentNotDeletableException
import kr.jay.kotlinboard.exception.CommentNotUpdatableException
import kr.jay.kotlinboard.exception.PostNotFoundException
import kr.jay.kotlinboard.repository.CommentRepository
import kr.jay.kotlinboard.repository.PostRepository
import kr.jay.kotlinboard.service.dto.CommentCreateRequestDto
import kr.jay.kotlinboard.service.dto.CommentUpdateRequestDto
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

/**
 * CommentServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    given("댓글 생성시") {
        val savedPost = postRepository.save(Post(title = "title1", content = "content1", createdBy = "jay"))
        When("정상적인 요청시") {
            val commentId = commentService.createComment(
                savedPost.id,
                CommentCreateRequestDto(
                    content = "댓글 내용",
                    createdBy = "jay",
                )
            )
            then("정상 생성됨") {
                commentId shouldBeGreaterThan 0L
                val comment = commentRepository.findByIdOrNull(commentId)
                comment shouldNotBe null
                comment?.content shouldBe "댓글 내용"
                comment?.createdBy shouldBe "jay"
            }
        }
        When("게시글이 존재하지 않으면") {
            then("게시글이 존재하지 않음 예외 발생") {
                shouldThrow<PostNotFoundException> {
                    commentService.createComment(
                        99999999L,
                        CommentCreateRequestDto(
                            content = "댓글 내용",
                            createdBy = "jay",
                        )
                    )
                }
            }
        }
    }
    given("댓글 수정시"){
        val savedPost = postRepository.save(Post(title = "title1", content = "content1", createdBy = "jay"))
        val savedComment = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
        When("인풋이 정상적으로 들어오면"){
            val updatedCommentId = commentService.updateComment(
                savedComment.id,
                CommentUpdateRequestDto(
                    content = "수정된 댓글 내용",
                    updatedBy = "jay",
                )
            )
            then("정상 수정됨"){
                updatedCommentId shouldBeEqual savedComment.id
                val updatedComment = commentRepository.findByIdOrNull(updatedCommentId)
                updatedComment shouldNotBe null
                updatedComment?.content shouldBe "수정된 댓글 내용"
                updatedComment?.createdBy shouldBe "jay"
            }
        }
        When("작성자와 수정자가 다르면"){
            then("수정할 수 없는 댓글 예외 발생"){
                shouldThrow<CommentNotUpdatableException> {
                    commentService.updateComment(
                        savedComment.id,
                        CommentUpdateRequestDto(
                            content = "수정된 댓글 내용",
                            updatedBy = "kay",
                        )
                    )
                }
            }
        }
    }
    given("댓글 삭제시"){
        val savedPost = postRepository.save(Post(title = "title1", content = "content1", createdBy = "jay"))
        val savedComment1 = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
        val savedComment2 = commentRepository.save(Comment(content = "댓글 내용", post = savedPost, createdBy = "jay"))
        When("인풋이 정상적으로 들어오면"){
            val deletedCommentId = commentService.deleteComment(
                savedComment1.id,
                "jay"
            )
            then("정상 삭제됨"){
                deletedCommentId shouldBeEqual savedComment1.id
                commentRepository.findByIdOrNull(deletedCommentId) shouldBe null
            }
        }
        When("작성자와 삭제자가 다르면"){
            then("삭제할 수 없는 댓글 예외 발생"){
                shouldThrow<CommentNotDeletableException> {
                    commentService.deleteComment(
                        savedComment2.id,
                        "kay"
                    )
                }
            }
        }

    }
}) {
}
