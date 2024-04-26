package kr.jay.payment.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.jay.payment.exception.NoProductFound
import kr.jay.payment.model.Product
import kr.jay.payment.repository.ProductInOrderRepository
import kr.jay.payment.repository.ProductRepository
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * OrderServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/26/24
 */
private val logger = KotlinLogging.logger {}
@SpringBootTest
class OrderServiceTest(
    @Autowired orderService: OrderService,
    @Autowired productRepository: ProductRepository,
    @Autowired productInOrderRepository: ProductInOrderRepository,

): StringSpec({

    beforeTest{
        productRepository.save(Product(1, "apple", 1000).apply { new = true })
        productRepository.save(Product(2, "banana", 1200).apply { new = true })
        productRepository.save(Product(3, "mango", 700).apply { new = true })
        productRepository.save(Product(4, "orange", 2100).apply { new = true })
    }

    "create on fail"{
        val request = ReqCreateOrder(
            11, listOf(
                ReqProductQuantity(1, 1),
                ReqProductQuantity(2, 2),
                ReqProductQuantity(3, 3),
                ReqProductQuantity(4, 4),
                ReqProductQuantity(5, 5),
            )
        )

        shouldThrow<NoProductFound>{
            orderService.create(request)
        }
    }

    "create"{
        val request = ReqCreateOrder(
            11, listOf(
                ReqProductQuantity(1, 1),
                ReqProductQuantity(2, 2),
                ReqProductQuantity(3, 3),
                ReqProductQuantity(4, 4),
            )
        )


        val order = orderService.create(request).also { logger.debug { it } }
        order.amount shouldBe 13900
        order.description shouldNotBe null
        order.pgOrderId shouldNotBe null

        productInOrderRepository.countByOrderId(order.id) shouldBe 4
    }
})