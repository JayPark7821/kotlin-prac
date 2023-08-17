package kr.jay.todo.api

import kr.jay.todo.api.model.TodoListResponse
import kr.jay.todo.api.model.TodoRequest
import kr.jay.todo.api.model.TodoResponse
import kr.jay.todo.service.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

/**
 * TodoController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getAll() =
        ok(TodoListResponse.of(todoService.findAll( )))

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id : Long) =
        ok(todoService.findById(id))

    @PostMapping
    fun create(@RequestBody request: TodoRequest)=
        ok(TodoResponse.of(todoService.create(request)))

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody request: TodoRequest
    ) = ok(TodoResponse.of(todoService.update(id, request)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        todoService.delete(id)
        return noContent().build()
    }

}