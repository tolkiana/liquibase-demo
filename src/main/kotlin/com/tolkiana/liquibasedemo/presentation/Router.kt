package com.tolkiana.liquibasedemo.presentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Router(private val handler: Handler) {
    @Bean("routes")
    fun routes(): RouterFunction<ServerResponse> {
        return router {
            GET("/products", handler::getAllProducts)
            GET("/products/{product_id}/sizes", handler::getProductSizes)
            GET("/products/{product_id}/colors", handler::getProductColors)
            GET("/sizes", handler::getAllSizes)
            GET("/colors", handler::getAllColors)
            POST("/products", handler::saveProduct)
        }
    }
}