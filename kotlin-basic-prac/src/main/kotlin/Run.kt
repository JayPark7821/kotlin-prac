class DatabaseClient {
    var url: String? = null
    var username: String? = null
    var password: String? = null

    fun connect(): Boolean {
        println("connecting... database")
        Thread.sleep(1000)
        println("database connected")
        return true
    }
}

fun main() {

//    val config = DatabaseClient()
//    config.url = "localhost:3306"
//    config.username = "admin"
//    config.password = "admin"
//    val connected : Boolean = config.connect()
    val connected: Boolean = DatabaseClient().run {
        url = "localhost:3306"
        username = "admin"
        password = "admin"
        connect()
    }


}
