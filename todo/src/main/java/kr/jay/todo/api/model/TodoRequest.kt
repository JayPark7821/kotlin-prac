package kr.jay.todo.api.model

/**
 * TodoRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
data class TodoRequest(
    val title: String,
    val description: String,
    val done: Boolean

) {
}