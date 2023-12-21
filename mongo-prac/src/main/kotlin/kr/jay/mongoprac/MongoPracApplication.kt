package kr.jay.mongoprac

import kr.jay.mongoprac.category.Category
import kr.jay.mongoprac.category.CategoryRepository
import kr.jay.mongoprac.product.Product
import kr.jay.mongoprac.product.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MongoPracApplication {
    @Bean
    fun commandLineRunner(
        productRepository: ProductRepository,
        categoryRepository: CategoryRepository
	): CommandLineRunner {
        return CommandLineRunner { args ->
            Category("Laptop", "Laptop").also { categoryRepository.save(it) }
            Category("cat1", "cat 2").also { categoryRepository.save(it) }
            Product("Macbook Pro", "Apple Laptop").also { productRepository.save(it) }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<MongoPracApplication>(*args)
}

