package reflection.annotation

/**
 * RequestMapping
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestMapping(
    val value: String = "",
    val method: Array<RequestMethod> = [],
)
