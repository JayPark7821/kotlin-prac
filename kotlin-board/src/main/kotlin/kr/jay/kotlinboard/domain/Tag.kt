package kr.jay.kotlinboard.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy

/**
 * Tag
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/08
 */
@Entity
class Tag(
    name: String,
    post: Post,
    createdBy: String,
) : BaseEntity(createdBy){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var name: String = name
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        private set
}
