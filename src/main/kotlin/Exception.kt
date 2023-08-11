import java.lang.Exception

fun main(){
    Thread.sleep(1)

    try{
        throw Exception()
    } catch (e: Exception){
        println("Exception")
    } finally {
        println("Finally")
    }

    val a = try{
        "1234".toInt()
    }catch (e: Exception){
      println("Exception")
    }
    println(a)

//    failFast("fefdf")

    val b : String? = null
    val c : String = b ?: failFast("fefdf")

    print(c.length)
}

fun failFast(message: String) : Nothing{
    throw IllegalStateException(message)
}