package kr.secretexcahnge.secretexchangesearch.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * AwsCredentialConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/10/24
 */

@Configuration
class AwsCredentialConfig {

    @Value("\${cloud.aws.credentials.access-key}")
    lateinit var accessKey: String
    @Value("\${cloud.aws.credentials.secret-key}")
    lateinit var secretKey: String

    @Bean
    fun awsCredentialsProvider(): AWSCredentialsProvider {
        return AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
    }
}