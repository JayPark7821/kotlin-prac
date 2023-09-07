package kr.jay.kotlinboard.exception

/**
 * CommentException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
open class CommentException(message: String) : RuntimeException(message)

class CommentNotUpdatableException  : CommentException("댓글을 수정할 수 없습니다.")

class CommentNotFoundException : CommentException("댓글을 찾을 수 없습니다.")

class CommentNotDeletableException : CommentException("삭제할 수 없는 댓글 입니다.")
