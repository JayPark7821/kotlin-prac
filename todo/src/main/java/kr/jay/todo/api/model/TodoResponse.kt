package kr.jay.todo.api.model

import kr.jay.todo.domain.Todo
import java.time.LocalDateTime

/**
 * TodoResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val done: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun of(todo: Todo?) : TodoResponse {
            checkNotNull(todo) { "todo must not be null" }
            checkNotNull(todo.id) { "todo Id must not be null" }

            return TodoResponse(
                id = todo.id,
//                id = todo.id!!,
                title = todo.title,
                description = todo.description,
                done = todo.done,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        }


    }
}