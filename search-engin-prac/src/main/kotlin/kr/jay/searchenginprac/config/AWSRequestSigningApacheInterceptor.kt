package kr.secretexcahnge.secretexchangesearch.config

import com.amazonaws.DefaultRequest
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.Signer
import com.amazonaws.http.HttpMethodName
import org.apache.http.*
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.message.BasicHeader
import org.apache.http.protocol.HttpContext
import org.apache.http.protocol.HttpCoreContext.HTTP_TARGET_HOST
import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.*


/**
 * AWSRequestSigningApacheInterceptor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/10/24
 */

class AWSRequestSigningApacheInterceptor(
    private val serviceName: String,
    private val signer: Signer,
    private val awsCredentialsProvider: AWSCredentialsProvider,
) : HttpRequestInterceptor {

    override fun process(request: HttpRequest, context: HttpContext) {
        val uriBuilder: URIBuilder
        try {
            uriBuilder = URIBuilder(request.requestLine.uri)
        } catch (e: URISyntaxException) {
            throw IOException("Invalid URI", e)
        }


        // Copy Apache HttpRequest to AWS DefaultRequest
        val signableRequest = DefaultRequest<Any>(serviceName)

        val host = context.getAttribute(HTTP_TARGET_HOST)?.let { it as HttpHost }
        if (host != null) {
            signableRequest.endpoint = URI.create(host.toURI())
        }
        val httpMethod =
            HttpMethodName.fromValue(request.requestLine.method)
        signableRequest.httpMethod = httpMethod
        try {
            signableRequest.setResourcePath(uriBuilder.build().rawPath)
        } catch (e: URISyntaxException) {
            throw IOException("Invalid URI", e)
        }

        if (request is HttpEntityEnclosingRequest) {
            if (request.entity == null) {
                signableRequest.setContent(ByteArrayInputStream(ByteArray(0)))
            } else {
                signableRequest.setContent(request.entity.content)
            }
        }
        signableRequest.parameters = nvpToMapParams(uriBuilder.queryParams)
        signableRequest.headers = headerArrayToMap(request.allHeaders)


        // Sign it
        signer.sign(signableRequest, awsCredentialsProvider.credentials)


        // Now copy everything back
        request.setHeaders(mapToHeaderArray(signableRequest.headers))
        if (request is HttpEntityEnclosingRequest && request.entity != null) {
            val basicHttpEntity = BasicHttpEntity()
            basicHttpEntity.content = signableRequest.content
            request.entity = basicHttpEntity
        }
    }

    /**
     * @param params list of HTTP query params as NameValuePairs
     * @return a multimap of HTTP query params
     */
    private fun nvpToMapParams(
        params: List<NameValuePair>
    ): Map<String, MutableList<String>> {
        val parameterMap: MutableMap<String, MutableList<String>> =
            TreeMap(java.lang.String.CASE_INSENSITIVE_ORDER)
        for (nvp in params) {
            val argsList =
                parameterMap.computeIfAbsent(
                    nvp.name
                ) { _: String? -> ArrayList() }
            argsList.add(nvp.value)
        }
        return parameterMap
    }

    /**
     * @param headers modeled Header objects
     * @return a Map of header entries
     */
    private fun headerArrayToMap(headers: Array<Header>): Map<String, String> {
        val headersMap: MutableMap<String, String> = TreeMap(java.lang.String.CASE_INSENSITIVE_ORDER)
        for (header in headers) {
            if (!skipHeader(header)) {
                headersMap[header.name] = header.value
            }
        }
        return headersMap
    }

    /**
     * @param header header line to check
     * @return true if the given header should be excluded when signing
     */
    private fun skipHeader(header: Header): Boolean {
        return (("content-length".equals(
            header.name,
            ignoreCase = true
        ) && "0" == header.value) // Strip Content-Length: 0
            || "host".equals(header.name, ignoreCase = true) // Host comes from endpoint
            )
    }

    /**
     * @param mapHeaders Map of header entries
     * @return modeled Header objects
     */
    private fun mapToHeaderArray(mapHeaders: Map<String, String>): Array<Header?> {
        val headers: Array<Header?> = arrayOfNulls<Header>(mapHeaders.size)
        var i = 0
        for ((key, value) in mapHeaders) {
            headers[i++] = BasicHeader(key, value)
        }
        return headers
    }
}