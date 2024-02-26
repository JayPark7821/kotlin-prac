package mvc.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * HomeController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
class HomeController : Controller {
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse): String {
        return "home"
    }
}