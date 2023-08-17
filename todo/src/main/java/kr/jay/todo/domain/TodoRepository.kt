package kr.jay.todo.domain

import org.springframework.data.jpa.repository.JpaRepository

/**
 * TodoRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findAllByDoneIsFalseOrderByIdDesc() : List<Todo>?
}