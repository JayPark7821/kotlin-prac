package kr.jay.kopringboottemplate.common

import org.hibernate.resource.jdbc.spi.StatementInspector
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong


/**
 * HibernateInterceptor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@Component
class HibernateQueryCounter(
) : StatementInspector {

    companion object {
        val queryCount = ThreadLocal<Counter>()
    }

    fun start() {
        queryCount.set(Counter(AtomicLong(0), System.currentTimeMillis()))
    }

    fun getCount(): Counter {
        return queryCount.get()
    }

    fun clear() {
        queryCount.remove()
    }

    override fun inspect(sql: String): String {
        val counter = queryCount.get()
        if (counter != null) {
            val count: AtomicLong = counter.count
            count.addAndGet(1)
        }
        return sql
    }


    data class Counter (
        val count: AtomicLong = AtomicLong(0),
        val time: Long = System.currentTimeMillis(),
    )

}