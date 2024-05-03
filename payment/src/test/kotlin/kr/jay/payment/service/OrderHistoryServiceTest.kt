package kr.jay.payment.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kr.jay.payment.config.extension.toLocalDate
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus.*
import kr.jay.payment.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * OrderHistoryServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/3/24
 */
@SpringBootTest
@ActiveProfiles("test")
class OrderHistoryServiceTest(
    @Autowired private val orderHistoryService: OrderHistoryService,
    @Autowired private val orderRepository: OrderRepository,

    ) : StringSpec({
    "filter valid status" {
        listOf(
            Order(userId = 11, description = "a", amount = 1000, pgStatus = CREATE),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = AUTH_SUCCESS),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = AUTH_FAIL),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = AUTH_INVALID),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = CAPTURE_REQUEST),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = CAPTURE_RETRY),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = CAPTURE_FAIL),
            Order(userId = 11, description = "a", amount = 1000, pgStatus = CAPTURE_SUCCESS),
        ).forEach { orderRepository.save(it) }
        orderHistoryService.getHistories(QryOrderHistory(userId = 11)).size shouldBe 3
    }

    "get order history" {
        var createdAt = "2023-01-01".toLocalDate().atStartOfDay()
        listOf(
            Order(userId = 11, description = "A,B", amount = 1000),
            Order(userId = 11, description = "C", amount = 1100),
            Order(userId = 11, description = "D,E,F", amount = 1200),
            Order(userId = 11, description = "D,G,H", amount = 1300),
            Order(userId = 11, description = "I,J", amount = 1400),
            Order(userId = 11, description = "I,K", amount = 1500),
        ).forEach {
            it.pgStatus = CAPTURE_SUCCESS
            orderRepository.save(it)
            it.createdAt = createdAt
            createdAt = createdAt.plusDays(1)
            orderRepository.save(it)
        }
        orderHistoryService.getHistories(QryOrderHistory(userId = 11)).size shouldBe 6
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "A")).size shouldBe 1
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "B")).size shouldBe 1
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "A")).first().id shouldBe
            orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "B")).first().id
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "D")).size shouldBe 2
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "D, H")).size shouldBe 1

        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "I", fromAmount = 1400)).size shouldBe 2
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, keyword = "I", fromAmount = 1450)).size shouldBe 1

        orderHistoryService.getHistories(QryOrderHistory(userId = 11, fromDate = "2023-01-03")).size shouldBe 4
        orderHistoryService.getHistories(QryOrderHistory(userId = 11, fromDate = "2023-01-03", toDate = "2023-01-05")).size shouldBe 3



    }
})