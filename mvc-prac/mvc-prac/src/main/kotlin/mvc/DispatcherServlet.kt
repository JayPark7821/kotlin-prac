package mvc

import org.slf4j.LoggerFactory
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

    override fun init() {
        requestMappingHandlerMapping.init()
    }

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        log.info("Dispatcher Servlet")
        val controller = requestMappingHandlerMapping.findHandler(request.requestURI)

        val viewName = controller.handleRequest(request, response)

        val requestDispatcher = request.getRequestDispatcher(viewName)

        requestDispatcher.forward(request, response)
    }
}