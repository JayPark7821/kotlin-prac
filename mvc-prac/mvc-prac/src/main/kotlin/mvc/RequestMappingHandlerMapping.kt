package mvc

import mvc.controller.Controller
import mvc.controller.HomeController

/**
 * RequestMappingHandlerMapping
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
class RequestMappingHandlerMapping {

    private var mapping = mutableMapOf<String, Controller>()

    fun init(){
        mapping["/"] = HomeController()
    }

    fun findHandler(urlPath: String): Controller{
        return mapping[urlPath] ?: throw IllegalArgumentException("Not found")
    }
}