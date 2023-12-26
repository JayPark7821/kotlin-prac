package kr.jay.webfluxreactor.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Article
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/25/23
 */

@Table("TB_ARTICLE")
class Article(
    @Id
    var id: Long = 0,
    var title: String,
    var body: String,
    var authorId: Long,
) :BaseEntity(){
    override fun toString(): String =
        "Article(id=$id, title='$title', body='$body', authorId=$authorId, ${super.toString()})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Article
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

}

open class BaseEntity(
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
) {
    override fun toString(): String = "createdAt=$createdAt, updatedAt=$updatedAt"
}