package mvc

import mvc.view.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * HandlerAdapter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/27/24
 */
interface HandlerAdapter {
    fun supports(handler: Any): Boolean

    fun handle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): ModelAndView
}