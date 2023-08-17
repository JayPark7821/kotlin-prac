package kr.jay.todo.service

import kr.jay.todo.api.model.TodoRequest
import kr.jay.todo.domain.Todo
import kr.jay.todo.domain.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

/**
 * TodoService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */

@Service
class TodoService (
    val todoRepository: TodoRepository
){

    @Transactional(readOnly = true)
    fun findAll(): List<Todo> =
        todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))

    @Transactional(readOnly = true)
    fun findById(id: Long): Todo =
        todoRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Transactional
    fun create(request : TodoRequest?) : Todo{
        checkNotNull(request) { "request must not be null" }
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                done = request.done,
                createdAt = LocalDateTime.now()
            )
        )
    }

    @Transactional
    fun update(
        id: Long,
        request: TodoRequest?
    ) : Todo{
        checkNotNull(request) { "request must not be null" }
        return findById(id).let {
            it.update(
                request.title,
                request.description,
                request.done
            )
            todoRepository.save(it)
        }
    }

    @Transactional
    fun delete(id: Long) =
        todoRepository.deleteById(id)
}