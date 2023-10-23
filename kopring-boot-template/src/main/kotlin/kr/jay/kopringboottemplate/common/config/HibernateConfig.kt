package kr.jay.kopringboottemplate.common.config

import org.hibernate.cfg.AvailableSettings
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
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
class HibernateConfig {

    @Bean
    fun configureStatementInspector() : HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties ->
            hibernateProperties[AvailableSettings.STATEMENT_INSPECTOR] = "kr.jay.kopringboottemplate.common.HibernateQueryCounter"
        }
    }
}