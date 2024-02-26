package mvc.view

/**
 * JspViewResolver
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
class JspViewResolver: ViewResolver {
    override fun resolve(viewName: String): View {
        if( viewName.startsWith("redirect:")) {
            return RedirectView(viewName)
        }
        return JpsView("$viewName.jsp")
    }
}