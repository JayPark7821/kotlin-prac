package kotlinjava.jvmstatic

/**
 * JvmFieldClass
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
class JvmFieldClass {
    companion object {
        @JvmField
        val id = 1234
        const val CODE = 1234
    }
}

object JvmFieldObject{
    val name = "jay"
    const val FINAL_NAME = "jay"
}

fun main(){
    val id = JvmFieldClass.id
    val name = JvmFieldObject.name

}