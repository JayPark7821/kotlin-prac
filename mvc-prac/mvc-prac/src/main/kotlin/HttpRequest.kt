import java.io.BufferedReader

/**
 * HttpRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
class HttpRequest(
    bufferedReader: BufferedReader
) {

    val requestLine: RequestLine =
        RequestLine(bufferedReader.readLine())

    fun isGetRequest() = requestLine.isGetRequest()

    fun matchPath(path: String) = requestLine.matchPath(path)

    fun getQueryStrings() = requestLine.getQueryStrings()
}