package mvc.controller

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
        request.setAttribute("users", listOf<String>())
        return "/user/list.jsp"
    }

}