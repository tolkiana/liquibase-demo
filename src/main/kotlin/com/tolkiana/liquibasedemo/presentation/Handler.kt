package com.tolkiana.liquibasedemo.presentation

import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.data.SizeRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class Handler(
    private val sizeRepository: SizeRepository,
    private val colorRepository: ColorRepository,
    private val productRepository: ProductRepository
) {
    fun getAllProducts(request: ServerRequest): Mono<ServerResponse> =
            ok().json().body(productRepository.findAll(), Product::class.java)

    fun getAllSizes(request: ServerRequest): Mono<ServerResponse> =
            ok().json().body(sizeRepository.findAll(), Size::class.java)

    fun getAllColors(request: ServerRequest): Mono<ServerResponse> =
            ok().json().body(colorRepository.findAll(), Color::class.java)

    fun getProductSizes(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            sizeRepository.findByProduct(it.pathVariable("product_id").toInt())
        }.flatMap {
            ok().json().body(it)
        }

    fun getProductColors(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            colorRepository.findByProduct(it.pathVariable("product_id").toInt())
        }.flatMap {
            ok().json().body(it)
        }

    fun saveProduct(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<Product>().map {
            productRepository.save(it)
        }.flatMap {
            ok().json().body(it)
        }
}
