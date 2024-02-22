package servlet

import org.slf4j.LoggerFactory
import javax.servlet.Servlet
import javax.servlet.ServletConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebServlet

/**
 * CalculatorServlet
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
@WebServlet("/calculate")
class CalculatorServlet : Servlet {

    private val logger = LoggerFactory.getLogger(CalculatorServlet::class.java)

    override fun init(p0: ServletConfig?) {
        logger.info("CalculatorServlet initialized")
    }

    override fun service(request: ServletRequest?, response: ServletResponse?) {
        logger.info("CalculatorServlet service called")
        val operand1 = request?.getParameter("operand1")?.toInt() ?: 0
        val operand2 = request?.getParameter("operand2")?.toInt() ?: 0
        val operator = request?.getParameter("operator")

        val result = when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> 0
        }
        response?.writer?.println(result)
    }

    override fun destroy() {
        logger.info("CalculatorServlet destroyed")
    }

    override fun getServletConfig(): ServletConfig {
        TODO("Not yet implemented")
    }

    override fun getServletInfo(): String {
        TODO("Not yet implemented")
    }

}