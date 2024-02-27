package mvc

import mvc.controller.Controller
import mvc.view.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * SimpleControllerHandlerAdapter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/27/24
 */
class SimpleControllerHandlerAdapter: HandlerAdapter {
    override fun supports(handler: Any): Boolean {
        return handler is Controller
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): ModelAndView {
        val controller = handler as Controller
        val viewName = controller.handleRequest(request, response)
        return ModelAndView(viewName)
    }
}