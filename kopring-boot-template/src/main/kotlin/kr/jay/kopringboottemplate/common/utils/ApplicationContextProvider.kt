package kr.jay.kopringboottemplate.common.utils

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

/**
 * ApplicationContextProvider
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */

@Configuration(proxyBeanMethods = false)
class ApplicationContextProvider: ApplicationContextAware{
    companion object{
        private lateinit var savedApplicationContext: ApplicationContext

        fun getApplicationContext(): ApplicationContext {
            return savedApplicationContext
        }
    }
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        savedApplicationContext = applicationContext
    }
}