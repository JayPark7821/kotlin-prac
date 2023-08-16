class HelloBot{
//    var greeting : String? = null
    val greeting: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        println("lazy init")
        getHello()
    }
    fun sayHello() = println(greeting)

}
fun getHello() = "hello"
fun main(){

    val helloBot = HelloBot()
//    helloBot.greeting = getHello()
    for ( i in 1..10){
        Thread{
            helloBot.sayHello()
        }.start()
    }
}