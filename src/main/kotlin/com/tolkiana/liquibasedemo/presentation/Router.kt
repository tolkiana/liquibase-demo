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
            POST("/products", handler::createProduct)
            GET("/products", handler::getAllProducts)
            GET("/products/{product_id}", handler::getProduct)
            PUT("/products/{product_id}", handler::updateProduct)
            DELETE("/products/{product_id}", handler::deleteProduct)
            GET("/products/{product_id}/sizes", handler::getProductSizes)
            GET("/products/{product_id}/colors", handler::getProductColors)
            GET("/sizes", handler::getAllSizes)
            GET("/colors", handler::getAllColors)
        }
    }
}