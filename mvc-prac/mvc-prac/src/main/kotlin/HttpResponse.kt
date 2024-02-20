import java.io.DataOutputStream

/**
 * HttpResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
class HttpResponse(
    val dos: DataOutputStream,
) {

    fun response200Header(contentType: String, lengthOfBodyContent: Int) {
        dos.writeBytes("HTTP/1.1 200 OK \r\n")
        dos.writeBytes("Content-Type: $contentType;charset=utf-8\r\n")
        dos.writeBytes("Content-Length: $lengthOfBodyContent\r\n")
        dos.writeBytes("\r\n")
    }

    fun responseBody(body: ByteArray) {
        dos.write(body, 0, body.size)
        dos.flush()
    }
}