package kr.jay.kopringboottemplate.interfaces

import kr.jay.kopringboottemplate.domain.Users
import kr.jay.kopringboottemplate.domain.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun join(@RequestBody request: UserJoinRequest) =
        UserResponse(userService.savUser(request.name))
}

data class UserResponse(
    val id: Long,
    val name: String,
){
    constructor(users: Users):
    this(
        id = users.id,
        name = users.name,
    )
}

data class UserJoinRequest(
    val name: String,
)


