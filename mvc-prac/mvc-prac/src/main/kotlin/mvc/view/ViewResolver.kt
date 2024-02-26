package mvc.view

/**
 * ViewResolver
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
interface ViewResolver {
    fun resolve(viewName: String): View
}