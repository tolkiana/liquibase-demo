package com.tolkiana.liquibasedemo.application

import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.models.Size
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository
) {

    fun getAllProducts(): Flux<Product> {
        return productRepository.findAll()
    }

    fun getProductColors(productId: Number): Flux<Color> {
        return colorRepository.findByProduct(productId)
    }

    fun getProductSizes(productId: Number): Flux<Size> {
        return sizeRepository.findByProduct(productId)
    }

    fun createProduct(product: Product): Mono<Product> {
        return productRepository.save(product)
    }
}
