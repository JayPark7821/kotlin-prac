fun main() {
    //자바 코드를 코틀린의 when식으로 변환한 코드
    val day = 2

    val result :String = when (day) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        else -> "Invalid day"
    }
    println(result)

    // else를 생략할 수 있다.
    when (getColor()) {
        Color.RED -> println("Color is Red")
        Color.GREEN -> println("Color is Green")
        else -> println("Color is Blue")
    }

    // 여러개의 조건을 콤마로 구분해 한줄에서 정의할 수 있다.
    when (getNumber()) {
        0, 1 -> print("0 또는 1")
        else -> print("0도 1도 아님")
    }
}

enum class Color{
    RED, GREEN, BLUE
}

fun getColor() = Color.RED

fun getNumber() = 2