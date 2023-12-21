package kr.jay.mongoprac.product

import org.springframework.web.bind.annotation.RestController

/**
 * ProductController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */

@RestController
class ProductController(
    private val service: ProductService
) {

    
}