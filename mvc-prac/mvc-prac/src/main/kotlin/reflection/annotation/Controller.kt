package reflection.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

/**
 * Controller
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Controller()
