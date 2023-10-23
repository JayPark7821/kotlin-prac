package kr.jay.kopringboottemplate.domain

import jakarta.persistence.*

@Entity
class Post(
    title: String,
    content: String,
    users : Users,
)  {
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
}
