package com.tolkiana.liquibasedemo.presentation

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class Handler {
    fun getProducts(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().json().bodyValue("Products")
    fun getSizes(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().json().bodyValue("Sizes")
}