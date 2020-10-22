package com.tolkiana.liquibasedemo.presentation

import com.tolkiana.liquibasedemo.data.Product
import com.tolkiana.liquibasedemo.data.ProductRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class Handler(private val productRepository: ProductRepository) {
    fun getProducts(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().json().body(productRepository.findAll(), Product::class.java)
    fun getSizes(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().json().bodyValue("Sizes")
}