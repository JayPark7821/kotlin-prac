package kr.jay.kopringboottemplate.common.config

import kr.jay.kopringboottemplate.common.HibernateQueryCounter
import kr.jay.kopringboottemplate.common.filter.HibernateQueryCounterFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * HibernateQueryCountFilterConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@Configuration
class HibernateQueryCountFilterConfig(
    private val log: org.slf4j.Logger,
    private val hibernateQueryCounter: HibernateQueryCounter
) {
    @Bean
    fun hibernateQueryCounterFilter(): FilterRegistrationBean<HibernateQueryCounterFilter> {
        val registrationBean = FilterRegistrationBean<HibernateQueryCounterFilter>()
        registrationBean.filter = HibernateQueryCounterFilter(hibernateQueryCounter,log)
        registrationBean.order = 1
        return registrationBean
    }
}