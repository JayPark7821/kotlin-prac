package kr.jay.elasticsearchprac.repository

import kr.jay.elasticsearchprac.model.History
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * HistoryRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/10/24
 */
@Repository
interface HistoryRepository: CoroutineCrudRepository<History, Long>
