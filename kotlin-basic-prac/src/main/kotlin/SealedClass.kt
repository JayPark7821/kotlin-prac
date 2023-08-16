sealed class Developers{

    abstract val name : String
    abstract fun code(language: String)
}

data class BackendDevelopers(override val name: String) : Developers(){
    override fun code(language: String) {
        println("Im backend-developer and code with $language")
    }
}

data class FrontendDevelopers(override val name: String) : Developers(){
    override fun code(language: String) {
        println("Im frontend-developer and code with $language")
    }
}

object OtherDevelopers : Developers(){
    override val name: String = "기타 개발자"


    override fun code(language: String) {
        TODO("Not yet implemented")
    }

}

object DeveloperPool{
    val pool = mutableMapOf<String, Developers>()
    fun add(developer: Developers) = when (developer) {
        is BackendDevelopers -> pool[developer.name] = developer
        is FrontendDevelopers -> pool[developer.name] = developer
        is OtherDevelopers -> println("익명의 개발자 입니다.")
//        else -> println("개발자가 아닙니다.")
    }

    fun get(name: String) = pool[name]
}

fun main(){
    val backendDevelopers = BackendDevelopers(name = "토니")
    DeveloperPool.add(backendDevelopers)

    val frontendDevelopers = FrontendDevelopers(name = "피터")
    DeveloperPool.add(frontendDevelopers)

    println(DeveloperPool.get("토니"))
    println(DeveloperPool.get("피터"))
}