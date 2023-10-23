package kr.jay.kopringboottemplate.common.config

import kr.jay.kopringboottemplate.common.filter.RequestTraceLoggingFilter
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.InterceptorRegistration

@Configuration
class RequestTraceLogFilterConfig(
    private val log: org.slf4j.Logger
) {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<RequestTraceLoggingFilter> {
        val registrationBean = FilterRegistrationBean<RequestTraceLoggingFilter>()
        registrationBean.filter = RequestTraceLoggingFilter(log)
        registrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return registrationBean
    }

}
