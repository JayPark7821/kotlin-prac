fun main(){
    val str = "hello"

    val length = with(str) {
        println(this)
        println(length)
        length
    }

    println(length)

}