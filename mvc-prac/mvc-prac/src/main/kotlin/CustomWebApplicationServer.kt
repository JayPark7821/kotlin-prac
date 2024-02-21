import mu.KotlinLogging
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.charset.StandardCharsets

/**
 * CustomWebApplicationServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/17/24
 */
private val logger = KotlinLogging.logger {}
class CustomWebApplicationServer(
    private val port: Int,
) {


    fun start() {
        val serverSocket = ServerSocket(port)
        logger.info("Server started at port $port")

        logger.info("Server waiting for client connection...")

        while (true) {
            val clientSocket = serverSocket.accept()
            logger.info("client connected")
            Thread(ClientRequestHandler(clientSocket)).start()
        }
    }
}