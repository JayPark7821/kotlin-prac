package mvc.controller

import mvc.model.User
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
class UserCreateController:Controller {
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse):String{
        UserRepository.save(User(request.getParameter("id"), request.getParameter("name")))
        return "redirect:/users"
    }
}