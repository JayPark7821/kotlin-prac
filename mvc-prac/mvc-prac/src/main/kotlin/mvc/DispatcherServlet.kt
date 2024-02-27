package mvc

import mvc.controller.RequestMethod
import mvc.view.JspViewResolver
import mvc.view.ViewResolver
import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * DispatcherServlet
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */

private val log = LoggerFactory.getLogger(DispatcherServlet::class.java)

@WebServlet("/")
class DispatcherServlet: HttpServlet(){

    private val requestMappingHandlerMapping = RequestMappingHandlerMapping()
    private lateinit var viewResolver: List<ViewResolver>
    private lateinit var handlerAdapters: List<HandlerAdapter>
    override fun init() {
        requestMappingHandlerMapping.init()
        viewResolver = Collections.singletonList(JspViewResolver())
        handlerAdapters = listOf(SimpleControllerHandlerAdapter())
    }

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        log.info("Dispatcher Servlet")
        val controller = requestMappingHandlerMapping.findHandler(
            HandlerKey(RequestMethod.valueOf(request.method),request.requestURI)
        )

        val modelAndView = (handlerAdapters.findLast { it.supports(controller) }
            ?.handle(request, response, controller)
            ?: throw ServletException("handler adapter not found"))

        viewResolver.forEach {
            val view = it.resolve(modelAndView.viewName()!!)
            view.render(modelAndView.model, request, response)
        }
    }
}