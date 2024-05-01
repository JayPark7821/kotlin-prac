package kr.jay.payment.common

import kr.jay.payment.repository.ProductInOrderRepository
import kr.jay.payment.service.OrderService
import kr.jay.payment.service.ProductService
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * Beans
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/28/24
 */
@Component
class Beans : ApplicationContextAware {

    companion object {
        lateinit var ctx: ApplicationContext
            private set

        fun <T: Any> getBean(byClass: KClass<T>, vararg args: Any): T {
            return ctx.getBean(byClass.java, args)
        }

        val beanProductInOrderRepository: ProductInOrderRepository by lazy {
            getBean(ProductInOrderRepository::class)
        }

        val beanProductService: ProductService by lazy {
            getBean(ProductService::class)
        }

        val beanOrderService: OrderService by lazy {
            getBean(OrderService::class)
        }


    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }
}