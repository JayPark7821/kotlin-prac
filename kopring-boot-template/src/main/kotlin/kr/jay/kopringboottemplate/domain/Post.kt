package kr.jay.kopringboottemplate.domain

import jakarta.persistence.*
import kr.jay.kopringboottemplate.common.jpa.BaseEntity
import kr.jay.kopringboottemplate.common.jpa.EntityChangeListeners
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class,EntityChangeListeners::class)
class Post(
    title: String,
    content: String,
    users : Users,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        private set

    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var users: Users = users
       private set

    override fun toString(): String {
        return "Post(" +
                "id=$id, " +
                "title='$title', " +
                "content='$content', " +
                "users=$users" +
                ")" +
                " ${super.toString()}"
    }


}
