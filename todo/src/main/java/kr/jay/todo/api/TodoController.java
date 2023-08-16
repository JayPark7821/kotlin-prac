package kr.jay.todo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.todo.api.model.TodoListResponse;
import kr.jay.todo.api.model.TodoRequest;
import kr.jay.todo.api.model.TodoResponse;
import kr.jay.todo.domain.Todo;
import kr.jay.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<TodoListResponse> getAll() {
        List<Todo> todos = todoService.findAll();
        return ResponseEntity.ok(TodoListResponse.of(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> get(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        Todo todo = todoService.create(request);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id,
                                               @RequestBody TodoRequest request) {
        Todo todo = todoService.update(id, request);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
