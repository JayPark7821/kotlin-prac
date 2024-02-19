import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * QueryStringTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
class QueryStringTest {

    @Test
    fun case1(){
        val queryString = QueryString("operand1","11")
        assertThat(queryString).isNotNull()
    }
}