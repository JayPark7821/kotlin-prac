fun main() {
    // if...else 사용
    val job = "Software Developer"

    if (job == "Software Developer") {
        println("You are a Software Developer")
    } else {
        println("You are not a Software Developer")
    }

    // 코틀린의 if...else는 표현식이다.
    val age: Int = 10
    var str = if (age > 10) {
        "성인"
    } else {
        "아이"
    }

    val a = 1
    val b = 2
    val c = if (b > a) b else a
}