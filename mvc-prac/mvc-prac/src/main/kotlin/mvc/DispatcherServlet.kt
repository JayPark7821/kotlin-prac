package mvc

import mvc.controller.RequestMethod
import mvc.view.JspViewResolver
import mvc.view.ViewResolver
import org.slf4j.LoggerFactory
import java.util.*
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
    override fun init() {
        requestMappingHandlerMapping.init()
        viewResolver = Collections.singletonList(JspViewResolver())
    }

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        log.info("Dispatcher Servlet")
        val controller = requestMappingHandlerMapping.findHandler(
            HandlerKey(RequestMethod.valueOf(request.method),request.requestURI)
        )

        val viewName = controller.handleRequest(request, response)
        viewResolver.forEach {
            val view = it.resolve(viewName)
            view.render(mapOf(), request, response)
        }
    }
}