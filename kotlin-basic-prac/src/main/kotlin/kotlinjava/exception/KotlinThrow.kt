package kotlinjava.exception

import java.io.IOException

/**
 * KotlinThrow
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
class KotlinThrow {

//    @Throws(IOException::class)
    fun throwIOException(){
        throw IOException("Checked exception IOException")
    }
}

fun main(){
    val javaThrow = JavaThrow()
    javaThrow.throwIOException()

    val kotlinThrow = KotlinThrow()
    kotlinThrow.throwIOException()

}