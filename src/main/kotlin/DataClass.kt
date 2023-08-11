data class Person(val name: String, val age: Int)

fun main(){
    val person = Person(name = "홍길동", age = 20)

    val (name, age) = person
    println("이름=${name}, 나이=${age}")
}
