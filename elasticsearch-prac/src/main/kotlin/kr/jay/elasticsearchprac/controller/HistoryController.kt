package kr.jay.elasticsearchprac.controller

import kr.jay.elasticsearchprac.model.History
import kr.jay.elasticsearchprac.model.PgStatus
import kr.jay.elasticsearchprac.repository.HistoryNativeRepository
import kr.jay.elasticsearchprac.repository.HistoryRepository
import kr.jay.elasticsearchprac.repository.QrySearch
import kr.jay.elasticsearchprac.repository.ResSearch
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * HistoryController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/10/24
 */
@RestController
@RequestMapping("/history")
class HistoryController(
    private val repository: HistoryRepository,
    private val historyNativeRepository: HistoryNativeRepository,
) {

    @GetMapping("/{orderId}")
    suspend fun get(@PathVariable("orderId") orderId: Long) = repository.findById(orderId)

    @GetMapping("/all")
    suspend fun getAll() = repository.findAll()

    @PostMapping
    suspend fun save(@RequestBody request: ReqSaveHistory) {
        val history = repository.findById(request.orderId)?.let { history ->
            request.userId?.let { history.userId = it }
            request.description?.let { history.description = it }
            request.amount?.let { history.amount = it }
            request.pgStatus?.let { history.pgStatus = it }
            request.createdAt?.let { history.createdAt = it }
            request.updatedAt?.let { history.updatedAt = it }
            history
        } ?: request.toHistory()
        repository.save(history)
    }

    @DeleteMapping("/{orderId}")
    suspend fun delete(@PathVariable("orderId") orderId: Long) {
        repository.deleteById(orderId)
    }

    @DeleteMapping("/all")
    suspend fun deleteAll() {
        repository.deleteAll()
    }

    @GetMapping("/search")
    suspend fun search(request: QrySearch): ResSearch{
        return historyNativeRepository.search(request)
    }
}

data class ReqSaveHistory(
    var orderId: Long,
    var userId: Long?,
    var description: String?,
    var amount: Long?,
    var pgStatus: PgStatus?,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
    fun toHistory(): History {
        return this.let {
            History(
                orderId = it.orderId,
                userId = it.userId ?: 0,
                description = it.description ?: "",
                amount = it.amount ?: 0,
                pgStatus = it.pgStatus ?: PgStatus.CREATE,
                createdAt = it.createdAt ?: LocalDateTime.now(),
                updatedAt = it.updatedAt ?: LocalDateTime.now(),
            )
        }
    }
}
