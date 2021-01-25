package com.tolkiana.liquibasedemo.presentation

import com.tolkiana.liquibasedemo.data.Color
import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.Product
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.Size
import com.tolkiana.liquibasedemo.data.SizeRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class Handler(
    private val sizeRepository: SizeRepository,
    private val colorRepository: ColorRepository,
    private val productRepository: ProductRepository
) {
    fun getProducts(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().json().body(productRepository.findAll(), Product::class.java)
    fun getSizes(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().json().body(sizeRepository.findAll(), Size::class.java)
    fun getColors(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().json().body(colorRepository.findAll(), Color::class.java)
}
