package kr.jay.elasticsearchprac

import kotlinx.coroutines.runBlocking
import kr.jay.elasticsearchprac.config.extension.toLocalDate
import kr.jay.elasticsearchprac.model.History
import kr.jay.elasticsearchprac.model.PgStatus
import kr.jay.elasticsearchprac.model.PgStatus.*
import kr.jay.elasticsearchprac.repository.HistoryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ElasticsearchApplicationTests(
    @Autowired private val repository: HistoryRepository,
) {

    @Test
    fun contextLoads() {
        runBlocking {
            listOf(
                History( 1 ,  11 ,  "apple"           ,  1000 ,  CAPTURE_REQUEST,   "2023-01-01".toLocalDateTime()),
                History( 2 ,  11 ,  "mango"           ,  1100 ,  CAPTURE_RETRY,     "2023-01-02".toLocalDateTime()),
                History( 3 ,  11 ,  "orange,mango"    ,  1200 ,  CAPTURE_SUCCESS,   "2023-01-03".toLocalDateTime()),
                History( 4 ,  11 ,  "pineapple,mango" ,  1300 ,  CAPTURE_REQUEST ,  "2023-01-04".toLocalDateTime()),
                History( 5 ,  11 ,  "banana,mango"    ,  1400 ,  CAPTURE_RETRY   ,  "2023-01-05".toLocalDateTime()),
                History( 6 ,  11 ,  "crown"           ,  1500 ,  CAPTURE_SUCCESS ,  "2023-01-06".toLocalDateTime()),
                History( 7 ,  11 ,  "car"             ,  1600 ,  CAPTURE_REQUEST ,  "2023-01-07".toLocalDateTime()),
                History( 8 ,  11 ,  "tomato"          ,  1700 ,  CAPTURE_RETRY   ,  "2023-01-08".toLocalDateTime()),
                History( 9 ,  11 ,  "potato"          ,  1800 ,  CAPTURE_SUCCESS ,  "2023-01-09".toLocalDateTime()),
                History(10 ,  11 ,  "fried egg"       ,  1900 ,  CAPTURE_REQUEST ,  "2023-01-10".toLocalDateTime()),
                History(11 ,  11 ,  "egg scramble"    ,  2000 ,  CAPTURE_RETRY   ,  "2023-01-11".toLocalDateTime()),
                History(12 ,  11 ,  "boiled egg"      ,  2100 ,  CAPTURE_SUCCESS ,  "2023-01-12".toLocalDateTime()),

                History(21 ,  12 ,  "apple"           ,  1000 ,  CAPTURE_REQUEST ,  "2023-02-01".toLocalDateTime()),
                History(23 ,  12 ,  "orange,mango"    ,  1200 , CAPTURE_SUCCESS,  "2023-02-03".toLocalDateTime()),
                History(22 ,  12 ,  "mango"           ,  1100 ,  CAPTURE_RETRY   ,  "2023-02-02".toLocalDateTime()),
                History(24 ,  12 ,  "pineapple,mango" ,  1300 ,  CAPTURE_REQUEST ,  "2023-02-04".toLocalDateTime()),
                History(25 ,  12 ,  "banana,mango"    ,  1400 ,  CAPTURE_RETRY   ,  "2023-02-05".toLocalDateTime()),
                History(26 ,  12 ,  "crown"           ,  1500 ,  CAPTURE_SUCCESS ,  "2023-02-06".toLocalDateTime()),
                History(27 ,  12 ,  "car"             ,  1600 ,  CAPTURE_REQUEST ,  "2023-02-07".toLocalDateTime()),
                History(28 ,  12 ,  "tomato"          ,  1700 ,  CAPTURE_RETRY   ,  "2023-02-08".toLocalDateTime()),
                History(29 ,  12 ,  "potato"          ,  1800 ,  CAPTURE_SUCCESS ,  "2023-02-09".toLocalDateTime()),
                History(30 ,  12 ,  "fried egg"       ,  1900 ,  CAPTURE_REQUEST ,  "2023-02-10".toLocalDateTime()),
                History(31 ,  12 ,  "egg scramble"    ,  2000 ,  CAPTURE_RETRY   ,  "2023-02-11".toLocalDateTime()),
                History(32 ,  12 ,  "boiled egg"      ,  2100 ,  CAPTURE_SUCCESS ,  "2023-02-12".toLocalDateTime()),
            ).forEach {
                repository.save(it)
            }
        }
    }

}

fun String.toLocalDateTime(): LocalDateTime {
    return this.toLocalDate().atStartOfDay()
}