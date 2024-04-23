package kr.jay.webfluxcoroutine.service

import kr.jay.webfluxcoroutine.config.Locker
import kr.jay.webfluxcoroutine.model.Article
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kr.jay.webfluxcoroutine.exception.NoArticleFoundException as NoAccountFoundException
import kr.jay.webfluxcoroutine.repository.ArticleRepository as AccountRepository

/**
 * AccountService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/22/24
 */
@Service
class AccountService(
    private val repository: AccountRepository,
    private val locker: Locker,
) {

    suspend fun get(id: Long): ResAccount {
        return repository.findById(id)?.toResAccount() ?: throw NoAccountFoundException("id: $id")
    }

    @Transactional
    suspend fun deposit(id: Long, amount: Long) {
        val key = SimpleKey(AccountService::deposit.name, id)
        return locker.lock(key){
            repository.findArticleById(id)?.let { account ->
                account.balance += amount
                repository.save(account)
                repository.findArticleById(id)!!
            } ?: throw NoAccountFoundException("id: $id")
        }
    }
}

data class ResAccount(
    val id: Long,
    val balance: Long,
)

fun Article.toResAccount(): ResAccount {
    return ResAccount(
        id = this.id,
        balance = this.balance
    )
}