import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * RequestLineTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/17/24
 */
class RequestLineTest {

    @Test
    fun create(){
        val requestLine = RequestLine("GET /calculator?operand1=11&operator=*&operand2=55 HTTP/1.1")
        assertThat("requestLine").isNotNull()
        assertThat(requestLine.method).isEqualTo("GET")
        assertThat(requestLine.urlPath).isEqualTo("/calculator")
        assertThat(requestLine.queryStrings).isEqualTo("operand1=11&operator=*&operand2=55")
    }
}