import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import java.nio.charset.StandardCharsets

/**
 * ClientRequestHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/20/24
 */

class ClientRequestHandler(
    private val clientSocket: Socket
):Runnable {

    private val logger = LoggerFactory.getLogger(ClientRequestHandler::class.java)
    override fun run() {

        logger.info("new client ${Thread.currentThread().name} started")
        val inputStream = clientSocket.getInputStream()

        val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

        while (reader.ready()) {
            val httpRequest = HttpRequest(reader)
            val dos = DataOutputStream(clientSocket.getOutputStream())

            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculator")) {
                val userQueryStrings: UserQueryStrings? = httpRequest.getQueryStrings()
                val operand1 = userQueryStrings?.getValue("operand1")?.toInt() ?: 0
                val operand2 = userQueryStrings?.getValue("operand2")?.toInt() ?: 0
                val operator = userQueryStrings?.getValue("operator") ?: ""

                val result = when (operator) {
                    "+" -> operand1 + operand2
                    "-" -> operand1 - operand2
                    "*" -> operand1 * operand2
                    "/" -> operand1 / operand2
                    else -> 0
                }

                val body = result.toString().toByteArray(StandardCharsets.UTF_8)
                val response = HttpResponse(dos)
                response.response200Header("application/json", body.size)
                response.responseBody(body)
            }
        }
    }
}