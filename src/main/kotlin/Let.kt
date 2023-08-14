fun main(){
    val str: String? = "안녕"

    val let: Int? = str?.let {
        println(it)
        1234
    }
    println(let)
}