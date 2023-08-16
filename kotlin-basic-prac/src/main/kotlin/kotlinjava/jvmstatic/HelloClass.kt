package kotlinjava.jvmstatic

/**
 * HelloClass
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */

class HelloClass{
    companion object {
        @JvmStatic
        fun hello() = "hello!"
    }
}

object HiObject{
    fun hi() = "hi!"
}

fun main(){
    HelloClass.hello()
    HiObject.hi()
}
