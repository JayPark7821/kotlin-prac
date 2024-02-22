package servlet

import org.slf4j.LoggerFactory
import javax.servlet.Servlet
import javax.servlet.ServletConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * CalculatorHttpServlet
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
@WebServlet("/calculate-http")
class CalculatorHttpServlet : HttpServlet() {

    private val logger = LoggerFactory.getLogger(CalculatorHttpServlet::class.java)

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
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

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doPost(req, resp)
    }
}