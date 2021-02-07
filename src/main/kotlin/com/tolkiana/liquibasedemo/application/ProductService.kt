package com.tolkiana.liquibasedemo.application

import com.pacoworks.komprehensions.reactor.doFlatMapMono
import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import com.tolkiana.liquibasedemo.extensions.findDiff
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
            .flatMap { productRepository.insertProductSizes(it.id!!, sizeIds).then(it.toMono()) }
    }

    @Transactional
    fun deleteProduct(productId: Int): Mono<Void> {
        return productRepository
            .deleteAllProductColors(productId)
            .then(productRepository.deleteAllProductSizes(productId))
            .then(productRepository.deleteById(productId))
    }

    @Transactional
    fun updateProduct(updatedProduct: Product): Mono<Product> {
        val productId = updatedProduct.id?.toInt() ?: throw UnexpectedException()
        return doFlatMapMono(
            { getProductById(productId) },
            { currentProduct -> updateColors(currentProduct.colors, updatedProduct.colors, productId).collectList() },
            { currentProduct, _ -> updateSizes(currentProduct.sizes, updatedProduct.sizes, productId).collectList() },
            { _, _, _ -> productRepository.update(updatedProduct) },
            { _, newColors, newSizes, _ -> updatedProduct.copy(colors = newColors, sizes = newSizes).toMono() }
        )
    }

    fun getProductById(productId: Int): Mono<Product> {
        return doFlatMapMono(
            { productRepository.findById(productId) },
            { product -> colorRepository.findByProduct(product.id!!).collectList() },
            { product, _ -> sizeRepository.findByProduct(product.id!!).collectList() },
            { product, colors, sizes -> product.copy(colors = colors, sizes = sizes).toMono() }
        )
    }

    private fun updateColors(current: List<Color>, updated: List<Color>, productId: Number): Flux<Color> {
        val toDelete = current.map { it.id }.findDiff(updated.map { it.id })
        val toInsert = updated.map { it.id }.findDiff(current.map { it.id })
        return productRepository
            .deleteProductColors(productId, toDelete)
            .thenMany(productRepository.insertProductColors(productId, toInsert))
            .thenMany(colorRepository.findByProduct(productId))
    }

    private fun updateSizes(current: List<Size>, updated: List<Size>, productId: Number): Flux<Size> {
        val toDelete = current.map { it.id }.findDiff(updated.map { it.id })
        val toInsert = updated.map { it.id }.findDiff(current.map { it.id })
        return productRepository
            .deleteProductSizes(productId, toDelete)
            .thenMany(productRepository.insertProductSizes(productId, toInsert))
            .thenMany(sizeRepository.findByProduct(productId))
    }
}
