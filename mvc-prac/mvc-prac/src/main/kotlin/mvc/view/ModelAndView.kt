package mvc.view

/**
 * ModelAndView
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/27/24
 */
class ModelAndView(
    val view: Any,
) {
    val model: Map<String, Any> = mapOf()

    fun viewName(): String? {
        return if (this.view is String)
            this.view
        else
            null
    }
}