import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * QueryStringsTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
class QueryStringsTest {

    @Test
    fun case1(){
        val queryStrings = QueryStrings("operand1=11&operator=*&operand2=55")
        assertThat(queryStrings).isNotNull()
    }
}