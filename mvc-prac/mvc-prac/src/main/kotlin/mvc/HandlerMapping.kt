package mvc

import mvc.controller.Controller

/**
 * HandlerMapping
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/28/24
 */
interface HandlerMapping {
    fun findHandler(handlerKey: HandlerKey): Any
}