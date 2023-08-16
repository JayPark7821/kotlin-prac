fun String.first() : Char{
    return this[0]
}

fun String.addFirst(char: Char) : String{
    return char + this.substring(0)
}

class MyExample{
    fun printMessage() = println("class print")
}

fun MyExample.printMessage() = println("extension print")
fun MyExample?.printNullOrNotNull(){
    if(this == null) println("when null print")
    else println("when not null print")
}
fun main(){
    println("ABCD".first())
    println("ABCD".addFirst('Z'))
    MyExample().printMessage()
    var myExample: MyExample? = null
    myExample.printNullOrNotNull()
    myExample = MyExample()
    myExample.printNullOrNotNull()
}



