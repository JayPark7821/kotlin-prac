package kr.jay.webfluxcoroutine

import io.kotest.core.spec.style.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.ActiveProfiles
import org.springframework.core.io.support.EncodedResource
import org.springframework.r2dbc.connection.init.ScriptUtils

/**
 * ScriptTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/31/23
 */
@SpringBootTest
@ActiveProfiles("test")
class ScriptTest (
    @Autowired private val databaseClient: DatabaseClient
): StringSpec ({
    beforeSpec{
        val script = ClassPathResource("test.sql")
        databaseClient.inConnection {
                conn -> ScriptUtils.executeSqlScript(conn, script)
        }.subscribe()
    }
}){

}