class Coffee constructor(
    val name: String,
    val price: Int,
    var test: String = "test",
    var iced: Boolean = false,
) {

    val brand: String
        get() {
            return "코틀린"
        }

    var quantity : Int = 0
        set(value){
            if (value > 0) {
                field = value
            }
        }
}

class EmptyClass

fun main() {
    val coffee = Coffee(name = "아메리카노", price = 3000)
    coffee.test = "test2"
    coffee.quantity = 2
    coffee.iced = true

    if (coffee.iced) {
        println("아이스 커피 ")
    }

    println("${coffee.brand} ${coffee.name} 가격은 ${coffee.price}원 수량은 ${coffee.quantity}입니다.")

}