class LateInit{
    lateinit var text: String
    val textInitialized: Boolean
        get() = this::text.isInitialized

    fun printText(){
        if (this::text.isInitialized) {
            println("초기화")
        }else{
            text = "hello"
        }
        println(text)
    }
}

fun main(){
    val test = LateInit()
    if (!test.textInitialized) {
        test.text = "hi"
    }
    test.printText()
    test.printText()
}
