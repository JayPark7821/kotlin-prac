import java.time.LocalDateTime

object Singleton {
    val a = 1234
    fun printA() = println(a)
}

object DatetimeUtils { // object singleton 보통 유틸리티 클래스로 사용
    val now: LocalDateTime
        get() = LocalDateTime.now()

    const val DEFAULT_FORMAT = "YYYY-MM-DD"

    fun same(a: LocalDateTime, b: LocalDateTime): Boolean {
        return a == b
    }
}

// 동반 객체 (companion object)

class MyClass {
    private constructor()

    companion object MyCompanion {
        val a = 1234
        fun newInstance() = MyClass()
    }
}

fun main() {
    println(Singleton.a)
    Singleton.printA()

    println(DatetimeUtils.now)
    println(DatetimeUtils.now)
    println(DatetimeUtils.now)

    println(DatetimeUtils.DEFAULT_FORMAT)

    val now = LocalDateTime.now()
    println(DatetimeUtils.same(now, now))

    println(MyClass.a)
    println(MyClass.newInstance())
    println(MyClass.MyCompanion.a)
    println(MyClass.MyCompanion.newInstance())
}

