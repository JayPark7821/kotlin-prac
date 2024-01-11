package kr.secretexcahnge.secretexchangesearch.config

import com.amazonaws.auth.AWS4Signer
import com.amazonaws.auth.AWSCredentialsProvider
import org.apache.http.HttpHost

import org.opensearch.client.RestClient
import org.opensearch.client.RestHighLevelClient
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.time.Duration

/**
 * OpenSearchConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/10/24
 */
const val SERVICE_NAME = "es"

@Configuration
@EnableElasticsearchRepositories
class OpenSearchConfiguration(
    private val awsCredentialsProvider: AWSCredentialsProvider
) : AbstractOpenSearchConfiguration() {

    @Value("\${cloud.aws.open-search.endpoint}")
    lateinit var endPoint: String

    @Value("\${cloud.aws.open-search.region}")
    lateinit var region: String

    override fun opensearchClient(): RestHighLevelClient {
        val signer = AWS4Signer()
        signer.serviceName = SERVICE_NAME
        signer.regionName = region
        val interceptor = AWSRequestSigningApacheInterceptor(SERVICE_NAME, signer, awsCredentialsProvider)

        val config = RestClient.builder(HttpHost(endPoint, 443, "https"))
            .setHttpClientConfigCallback { httpClientBuilder -> httpClientBuilder.addInterceptorLast(interceptor) }
            .setRequestConfigCallback { requestConfigBuilder ->
                requestConfigBuilder
                    .setConnectTimeout(Duration.ofSeconds(300).toMillis().toInt())
                    .setSocketTimeout(Duration.ofSeconds(600).toMillis().toInt())
            }
        return RestHighLevelClient(config)
    }
}