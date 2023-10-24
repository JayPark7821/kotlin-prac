package kr.jay.kopringboottemplate.domain

import jakarta.persistence.*

/**
 * Tag
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/08
 */
@Entity
class Users(
    name: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var name: String = name
        private set
}
