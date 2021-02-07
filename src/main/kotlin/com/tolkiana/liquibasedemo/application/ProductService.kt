package com.tolkiana.liquibasedemo.application

import com.pacoworks.komprehensions.reactor.doFlatMap
import com.pacoworks.komprehensions.reactor.doFlatMapMono
import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.ProductColorRepository
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository,
    private val productColorRepository: ProductColorRepository
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

    @Transactional
    fun createProduct(product: Product): Mono<Product> {
        val colors = product.colors.map { it.id }
        return productRepository.save(product).flatMap {
            val productId = it.id ?: throw UnexpectedException()
            productColorRepository
                .insertProductColors(productId, colors)
                .then(it.toMono())
        }
    }
}
