package kr.jay.todo.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import kr.jay.todo.domain.Todo

/**
 * TodoListResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
data class TodoListResponse (
    val items: List<TodoResponse>
){
    val size : Int
        @JsonIgnore
        get() = items.size

    fun get(index: Int) = items[index]

    companion object{
        fun of(todoList: List<Todo>) =
            TodoListResponse(todoList.map{TodoResponse.of(it)})
    }
}