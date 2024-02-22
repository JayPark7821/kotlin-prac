package servlet

import org.apache.catalina.startup.Tomcat
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.charset.StandardCharsets
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * CalculatorServletWebApplicationServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */

private val logger = LoggerFactory.getLogger(CalculatorServlet::class.java)
class CalculatorServletWebApplicationServer()
fun main(args: Array<String>) {

    val webappDirLocation = "webapps/"
    Tomcat().apply {
        setPort(8080)
        addWebapp("/", File(webappDirLocation).absolutePath)
        logger.info("configuring app with basedir: " + File("./$webappDirLocation").absolutePath)

        start()
        server.await()
    }
}
