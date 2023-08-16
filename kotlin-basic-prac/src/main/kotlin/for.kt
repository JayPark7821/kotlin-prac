fun main(){
    // 범위 연산자 .. 를 사용해 for loop ehfflrl
    for (i in 1..10) {
        println(i)
    }

    // until을 사용해 반복한다.
    // 뒤에 온 숫자는 포함하지 않는다.
    for (i in 1 until 10) {
        println(i)
    }

    // step에 들어온 값 만큼 증가시킨다.
    for (i in 1..10 step 2) {
        println(i)
    }

    // downTo를 사용해서 반복하면서 값을 감소시킨다.
    for (i in 10 downTo 1) {
        println(i)
    }

    // 전달받은 배열을 반복
    val numbers = intArrayOf(1, 2, 3, 4, 5)
    for (i in numbers) {
        println(i)
    }
}