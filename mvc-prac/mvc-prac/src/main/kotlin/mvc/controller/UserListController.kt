package mvc.controller

import mvc.repository.UserRepository
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * UserListController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
class UserListController:Controller {
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse):String{
        request.setAttribute("users", UserRepository.findAll())
        return "/user/list"
    }

}