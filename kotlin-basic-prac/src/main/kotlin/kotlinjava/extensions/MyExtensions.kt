package kotlinjava.extensions

/**
 * MyExtensions
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */

fun String.first(): Char{
    return this[0]
}

fun String.addFirst(char: Char): String{
    return char + this.substring(0)
}

fun main(){
    println("ABCDEF".first())
    println("BCDEF".addFirst('A'))
}
