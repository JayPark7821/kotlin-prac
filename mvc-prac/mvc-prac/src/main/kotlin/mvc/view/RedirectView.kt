package mvc.view

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * RedirectView
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
private const val DEFAULT_REDIRECT_PREFIX = "redirect:"

class RedirectView(
    private val name: String,
) : View {
    override fun render(model: Map<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        response.sendRedirect(name.substring(DEFAULT_REDIRECT_PREFIX.length))
    }
}