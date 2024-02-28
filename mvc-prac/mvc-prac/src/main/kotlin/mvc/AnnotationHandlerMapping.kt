package mvc

/**
 * AnnotationHandlerMapping
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/28/24
 */
class AnnotationHandlerMapping: HandlerMapping {

    override fun findHandler(handlerKey: HandlerKey): Any {
        throw NotImplementedError("Not implemented")
    }
}