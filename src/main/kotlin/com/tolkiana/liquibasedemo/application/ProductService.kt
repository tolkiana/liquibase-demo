package com.tolkiana.liquibasedemo.application

import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.models.Size
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

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

    @Transactional
    fun createProduct(product: Product): Mono<Product> {
        val colorIds = product.colors.map { it.id }
        val sizeIds = product.sizes.map { it.id }
        return productRepository
            .save(product)
            .flatMap { productRepository.insertProductColors(it.id!!, colorIds).then(it.toMono()) }
            .flatMap { productRepository.insertProductSize(it.id!!, sizeIds).then(it.toMono()) }
    }

    @Transactional
    fun deleteProduct(productId: Int): Mono<Void> {
        return productRepository
            .deleteProductColors(productId)
            .then(productRepository.deleteProductSizes(productId))
            .then(productRepository.deleteById(productId))
    }
}
