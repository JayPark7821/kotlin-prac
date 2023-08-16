open class Dog{
    open var age: Int = 0

    open fun bark(){
        println("멍멍")
    }
}

open class Bulldog constructor(override var age: Int = 0) : Dog(){

    final override fun bark(){
        super.bark()
        println("불독이 짖습니다.")
    }

}

class ChildBulldog : Bulldog(){

    override var age: Int = 0

}

abstract class Developer{
    abstract var age: Int
    abstract fun code(language: String)
}

class BackendDeveloper(override var age: Int) : Developer(){

    override fun code(language: String) {
        println("I code with $language")
    }

}

fun main(){

    val bulldog = Bulldog(age = 2)
    println(bulldog.age)
    bulldog.bark()

    val backendDeveloper = BackendDeveloper(age = 20)
    println(backendDeveloper.age)
    backendDeveloper.code("Kotlin")
}
