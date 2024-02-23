package reflection

import org.junit.jupiter.api.Test
import org.reflections.Reflections
import org.slf4j.LoggerFactory
import reflection.annotation.Controller
import reflection.annotation.Service
import reflection.model.User

/**
 * ReflectionTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
class ReflectionTest {

    private val logger = LoggerFactory.getLogger(ReflectionTest::class.java)

    @Test
    fun controllerScan() {
        val reflections = Reflections("reflection")
        val beans = mutableSetOf<Class<*>>().apply {
            addAll(reflections.getTypesAnnotatedWith(Controller::class.java))
            addAll(reflections.getTypesAnnotatedWith(Service::class.java))
        }

        logger.info("beans: {}", beans)

    }

    @Test
    fun showClass(){
        val clazz = User::class.java

        logger.info("user fields : ${clazz.declaredFields.map { it.name }}")
        logger.info("user methods : ${clazz.declaredMethods.map { it.name }}")
    }

    @Test
    fun load(){
        //1
        val clazz = User::class.java

        //2
        val user = User(1, "jaypark")
        val clazz1 = user.javaClass

        //3
        val clazz2 = Class.forName("reflection.model.User")

        logger.info("clazz: $clazz \n clazz1: $clazz1 \n clazz2: $clazz2")
    }

}