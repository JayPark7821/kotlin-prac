package mvc.view

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * View
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
interface View {
    fun render(model: Map<String, Any>, request: HttpServletRequest, response: HttpServletResponse)
}