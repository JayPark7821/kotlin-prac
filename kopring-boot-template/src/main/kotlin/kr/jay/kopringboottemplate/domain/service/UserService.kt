package kr.jay.kopringboottemplate.domain.service

import kr.jay.kopringboottemplate.domain.Users
import kr.jay.kopringboottemplate.infrastructure.UserJpaRepository
import org.springframework.stereotype.Service

/**
 * UserServuce
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@Service
class UserService (
    private val userJpaRepository: UserJpaRepository
){
    fun savUser(name : String)
    = userJpaRepository.save(Users(name))

    fun getByUserId(id : Long)
    = userJpaRepository.findById(id)
        .orElseThrow { throw Exception("User not found") }
}