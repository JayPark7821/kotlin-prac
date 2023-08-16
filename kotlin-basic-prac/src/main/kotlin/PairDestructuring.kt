data class Tuple(val a : Int, val b :Int)

fun plus(tuple: Tuple) = tuple.a + tuple.b

fun plus(pair: Pair<Int, Int>) = pair.first + pair.second

fun main(){
    println(plus(Tuple(1,2)))
    println(plus(Pair(1,2)))

    val pair = Pair("A",1)
    val newPair = pair.copy(first = "A")
    println(newPair)

    val second = newPair.component2()
    println(second)

    val list = newPair.toList()
//    list.add()
    println(list)

    val triple = Triple("A","B","C")
    println(triple)
    triple.first
    triple.second
    triple.third

    val (a, b, c) = triple
    println("$a $b $c")

    val list1 = triple.toList()
    val (a1, b1, c1 ) = list1
    println("$a1 $b1 $c1")
    list1.component1()
    list1.component2()
    list1.component3()
    list1.component4()
    list1.component5()
//    list1.component6()

    val map = mutableMapOf("박재현" to "개발자")
    for((key, value) in map){
        println("${key} - ${value}")
    }
}