package kr.jay.searchenginprac

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ElasticsearchRestClientAutoConfiguration::class, ElasticsearchDataAutoConfiguration::class])
class SearchEnginPracApplication

fun main(args: Array<String>) {
    runApplication<SearchEnginPracApplication>(*args)
}
