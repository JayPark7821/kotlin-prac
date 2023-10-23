package kr.jay.kopringboottemplate.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.jay.kopringboottemplate.common.HibernateQueryCounter
import org.slf4j.Logger
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

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
        val duration: Long = System.currentTimeMillis() - counter.time
        val count: Long = counter.count.get()
        log.info("time : {}, count : {} , url : {}", duration, count, request.requestURI)
        if (count >= 10) {
            log.error("한 request 에 쿼리가 10번 이상 날라갔습니다.  날라간 횟수 : {} ", count)
        }
        hibernateQueryCounter.clear()
    }

}