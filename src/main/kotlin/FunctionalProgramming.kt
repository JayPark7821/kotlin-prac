fun main() {
    val list = mutableListOf(printHello)
    list[0]
    list[0]()

    val func = list[0]
    func()

    call(printHello)

    val result: Int = plus(1, 2)
    print(result)

    val list1 = listOf("a", "b", "c")
    val printStr : (String) -> Unit = { println(it) }

    forEachStr(list1, printStr)

    val func1 = outerFunc()
    func1()

    arg1{
        it.length
        it.first()
    }
    arg2{ a:String, b:String->
        a.length
        b.length
    }

    val callReference : () -> Unit = {printHello}
    callReference()

    val callReference1 = ::printHello
    callReference1()

    val numberList = listOf("1","2","3")
    numberList.map{it.toInt()}
        .forEach { println(it)  }

    numberList.map(String::toInt)
        .forEach(::println)


}

fun arg1(block: (String) -> Unit){}
fun arg2(block: (String, String) -> Unit){}

val sum: (Int, Int) ->  Int = { x, y -> x + y }
val sum2 =  {x: Int , y: Int -> x + y }
fun outerFunc(): () -> Unit =  { println("익명함수") }

fun forEachStr(collection: Collection<String>, action: (String) -> Unit) {
    for (item in collection) {
        action(item)
    }
}

val printMessage: (String) -> Unit = { message: String -> println(message) }
val printMessage2: (String) -> Unit = { message -> println(message) }
val printMessage3: (String) -> Unit = { println(it) }

val plus: (Int, Int) -> Int = { a, b -> a + b }


val printHello: () -> Unit = { println("Hello") }
//val func: () -> String = {"result"}

fun call(block: () -> Unit) {
    block()
}

fun printNo() = println("No!")