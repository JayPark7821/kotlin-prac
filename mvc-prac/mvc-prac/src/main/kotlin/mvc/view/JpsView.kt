package mvc.view

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * JpsView
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
class JpsView(
    private val viewName: String,
) : View {

    override fun render(model: Map<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        model.forEach(request::setAttribute)

        request.getRequestDispatcher(viewName)
            .forward(request, response)
    }
}