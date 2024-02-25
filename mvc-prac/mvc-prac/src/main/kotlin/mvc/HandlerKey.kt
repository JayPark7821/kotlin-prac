package mvc

import mvc.controller.RequestMethod

/**
 * HandlerKey
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
data class HandlerKey (
    val method: RequestMethod,
    val path:String,
)