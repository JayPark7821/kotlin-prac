package mvc.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Controller
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
interface Controller {
    fun handleRequest(request: HttpServletRequest, response: HttpServletResponse): String
}