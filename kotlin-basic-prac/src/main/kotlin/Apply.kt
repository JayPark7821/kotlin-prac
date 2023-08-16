fun main() {
    val client: DatabaseClient = DatabaseClient().apply {
        url = "localhost:3306"
        username = "admin"
        password = "admin"
    }
}
