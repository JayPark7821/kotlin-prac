package mvc.controller

import mvc.annotation.Controller
import mvc.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * HomeController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
@Controller
class HomeController {

    @RequestMapping(value = "/", method = [RequestMethod.GET])
    fun handleRequest(request: HttpServletRequest, response: HttpServletResponse): String {
        return "home"
    }
}