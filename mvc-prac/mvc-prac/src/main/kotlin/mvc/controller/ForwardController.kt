package mvc.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ForwardController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
class ForwardController(
    private val forwardUrl:String
):Controller {

    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse): String {
        return forwardUrl
    }
}