package kr.jay.todo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kr.jay.todo.domain.Todo
import kr.jay.todo.domain.TodoRepository
import kr.jay.todo.service.TodoService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

/**
 * TodoServiceTests
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
@ExtendWith(SpringExtension::class)
class TodoServiceTests {

    @MockkBean
    lateinit var repository: TodoRepository

    lateinit var service: TodoService

    val stub : Todo by lazy {
        Todo(
            id = 1L,
            title = "test",
            description = "test detail",
            done = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
    }

    @BeforeEach
    fun setUp(){
        service = TodoService(repository)
    }

    @Test
    fun `한개의 TODO를 반환해야한다`(){
        every { repository.findByIdOrNull(1) } returns stub

        val actual = service.findById(1)

        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(stub)
    }
}