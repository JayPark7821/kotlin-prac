/**
 * RequestLine
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/17/24
 */
class RequestLine constructor(
    /**
     * GET /calculator?operand1=11&operator=*&operand2=55 HTTP/1.1
     */
    private val requestLine: String,
){
    val method: String
    val urlPath: String
    val queryString: String?

    init {
        val token = requestLine.split(" ")
        this.method = token[0]
        val urlPathTokens = token[1].split("?")
        this.urlPath = urlPathTokens[0]

        if(urlPathTokens.size > 1)
            this.queryString = urlPathTokens[1]
        else
            this.queryString = null
    }
}