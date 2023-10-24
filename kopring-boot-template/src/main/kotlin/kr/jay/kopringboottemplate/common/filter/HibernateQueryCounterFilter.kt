package kr.jay.kopringboottemplate.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.jay.kopringboottemplate.common.HibernateQueryCounter
import org.slf4j.Logger
import org.springframework.web.filter.OncePerRequestFilter

class HibernateQueryCounterFilter(
    private val hibernateQueryCounter: HibernateQueryCounter,
    private val log : Logger
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        hibernateQueryCounter.start()
        filterChain.doFilter(request, response)
        val counter: HibernateQueryCounter.Counter = hibernateQueryCounter.getCount()
        val count: Long = counter.totalQueryCount.get()
        val queryMap = counter.occurredQuery
        log.info("count : {} , url : {}",   count, request.requestURI)
        if (count >= 10) {
            log.error("한 request 에 쿼리가 10번 이상 발생.  발생 횟수 : {} ", count)
        }
        if(queryMap.isNotEmpty()){
            queryMap.forEach { (sql, count) ->
                if(count.toLong()> 3){
                    log.error("한 request 에 동일 쿼리가 3번 이상 발생.  발생 횟수 : {} , 쿼리 : {}", count, sql)
                }
            }
        }
        hibernateQueryCounter.clear()
    }

}