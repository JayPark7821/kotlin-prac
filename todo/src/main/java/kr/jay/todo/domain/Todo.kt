package kr.jay.todo.domain

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Todo
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/17
 */
@Entity
@Table(name = "todos")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = 0,

    @Column(name ="title")
    var title : String,

    @Lob
    @Column(name = "description")
    var description : String,

    @Column(name = "done")
    var done : Boolean,

    @Column(name = "created_at")
    var createdAt : LocalDateTime,

    @Column(name = "updated_at")
    var updatedAt : LocalDateTime? = null
) {

    fun update(
        title: String,
        description: String,
        done: Boolean
    ) {
        this.title = title
        this.description = description
        this.done = done
        this.updatedAt = LocalDateTime.now()
    }
}