import org.slf4j.LoggerFactory
import java.net.ServerSocket

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

        while ((serverSocket.accept()) !=null){
            logger.info("client connected")
        }

    }
}