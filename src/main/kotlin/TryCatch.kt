fun getStr(): Nothing = throw Exception("exception")

fun main() {
    val result = try {
        getStr()
    } catch (e: Exception) {
        println("Exception")
        "default value"
    }

    val result2 = runCatching { getStr() }
        .getOrElse {
            println(it.message)
            "default value"
        }

    val result3 = runCatching { getStr() }
        .getOrNull()
    val result3_1 = runCatching { "성공" }
        .getOrNull()

    val result4: Throwable? = runCatching { getStr() }
        .exceptionOrNull()

    result4?.let {
        println(it.message)
        throw it
    }

    val result5 = runCatching { getStr() }
        .getOrDefault("defualt value")
    println(result5)

//    val result6 = runCatching { getStr() }
//        .getOrThrow()
//    println(result6)

    val result7 = runCatching { "result" }
        .map {
            "${it} returns"
        }.getOrThrow()
    println(result7)

    val result8 = runCatching { "result" }
        .map {
           throw Exception("exception")
        }.getOrDefault("default value")
    println(result8)

    val result9 = runCatching { "result" }
        .mapCatching {
           throw Exception("exception")
        }.getOrDefault("default value")
    println(result9)

    val result10 = runCatching { "result" }
        .recover { "recovered" }
        .getOrNull()
    println(result10)

    val result11 = runCatching { getStr() }
        .recover { "recovered" }
        .getOrNull()
    println(result11)

    val result12 = runCatching { getStr() }
        .recoverCatching {
            throw Exception("exception")
        }
        .getOrDefault("default value")
    println(result12)

    println(result)
    println(result2)
}