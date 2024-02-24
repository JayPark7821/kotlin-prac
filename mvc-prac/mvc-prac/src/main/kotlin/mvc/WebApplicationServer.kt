package mvc

import org.apache.catalina.startup.Tomcat
import org.slf4j.LoggerFactory
import java.io.File

/**
 * WebApplicationServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/24/24
 */
private val log = LoggerFactory.getLogger(WebApplicationServer::class.java)

class WebApplicationServer

fun main(args: Array<String>) {
    val webappDirLocation = "webapps/"
    Tomcat().apply {
        setPort(8080)
        addWebapp("/", File(webappDirLocation).absolutePath)
        log.info("configuring app with basedir: " + File("./$webappDirLocation").absolutePath)

        start()
        server.await()
    }
}