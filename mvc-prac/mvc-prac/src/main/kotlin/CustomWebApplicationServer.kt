import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.DataOutputStream
import java.net.ServerSocket
import java.nio.charset.StandardCharsets

/**
 * CustomWebApplicationServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/17/24
 */
val logger = LoggerFactory.getLogger(CustomWebApplicationServer::class.java)
class CustomWebApplicationServer(
    private val port: Int
) {


    fun start(){
        val serverSocket = ServerSocket(port)
        logger.info("Server started at port $port")

        logger.info("Server waiting for client connection...")

        while (true){
            val clientSocket = serverSocket.accept()

            logger.info("client connected")

            val inputStream = clientSocket.getInputStream()

            val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

            while(reader.ready()){
                val line = reader.readLine()
                logger.info("received: $line")
            }
            serverSocket.close()
        }
    }
}