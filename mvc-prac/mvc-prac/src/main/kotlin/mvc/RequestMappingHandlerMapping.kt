package mvc

import mvc.controller.*

/**
 * RequestMappingHandlerMapping
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
class RequestMappingHandlerMapping {

    private var mapping = mutableMapOf<HandlerKey, Controller>()

    fun init(){
        mapping[HandlerKey(RequestMethod.GET, "/")] = HomeController()
        mapping[HandlerKey(RequestMethod.GET,"/users")] = UserListController()
        mapping[HandlerKey(RequestMethod.POST,"/users")] = UserCreateController()
    }

    fun findHandler(handlerKey: HandlerKey): Controller{
        return mapping[handlerKey] ?: throw IllegalArgumentException("Not found")
    }
}