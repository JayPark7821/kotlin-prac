package kr.jay.kopringboottemplate.common.config

import kr.jay.kopringboottemplate.common.utils.HibernateQueryCounter
import kr.jay.kopringboottemplate.common.filter.HibernateQueryCounterFilter
import org.hibernate.cfg.AvailableSettings
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * HibernateConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */

@Configuration
class HibernateConfig(
    private val log: org.slf4j.Logger,
    private val hibernateQueryCounter: HibernateQueryCounter
) {

    @Bean
    fun configureStatementInspector() : HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties ->
            hibernateProperties[AvailableSettings.STATEMENT_INSPECTOR] = "kr.jay.kopringboottemplate.common.utils.HibernateQueryCounter"
        }
    }

    @Bean
    fun hibernateQueryCounterFilter(): FilterRegistrationBean<HibernateQueryCounterFilter> {
        val registrationBean = FilterRegistrationBean<HibernateQueryCounterFilter>()
        registrationBean.filter = HibernateQueryCounterFilter(hibernateQueryCounter,log)
        registrationBean.order = 1
        return registrationBean
    }
}