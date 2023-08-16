class User(val name: String, val password: String){
    fun validate(){
        if (name.isNotEmpty() && password.isNotEmpty()) {
            println("유효성 검사 통과")
        } else {
            println("유효성 검사 실패")
        }
    }
    fun printName() = println(name)
}

fun main(){
//    val user = User(name = "jay", password = "test")
//    user.validate()

    val user: User = User(name = "jay", password = "test").also {
        it.validate()
        it.printName()
    }
}
