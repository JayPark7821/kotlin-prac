import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.charset.StandardCharsets
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * CustomWebApplicationServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/17/24
 */

class CustomWebApplicationServer(
    private val port: Int,
) {
    private val logger = LoggerFactory.getLogger(CustomWebApplicationServer::class.java)

    private val executorService: ExecutorService = Executors.newFixedThreadPool(10)

    fun start() {
        val serverSocket = ServerSocket(port)
        logger.info("Server started at port $port")

        logger.info("Server waiting for client connection...")

        while (true) {
            val clientSocket = serverSocket.accept()
            logger.info("client connected")
            executorService.execute(ClientRequestHandler(clientSocket))
        }
    }
}