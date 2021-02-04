package com.tolkiana.liquibasedemo.presentation

import com.tolkiana.liquibasedemo.application.ColorService
import com.tolkiana.liquibasedemo.application.ProductService
import com.tolkiana.liquibasedemo.application.SizeService
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.presentation.mappers.ColorMapper
import com.tolkiana.liquibasedemo.presentation.mappers.DTOMapper
import com.tolkiana.liquibasedemo.presentation.mappers.ProductMapper
import com.tolkiana.liquibasedemo.presentation.mappers.SizeMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Component
class Handler(
    private val sizeService: SizeService,
    private val colorService: ColorService,
    private val productService: ProductService,
    private val colorMapper: ColorMapper,
    private val sizeMapper: SizeMapper,
    private val productMapper: ProductMapper
) {
    fun getAllProducts(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            productService.getAllProducts()
        }.map {
            productMapper.toDTOs(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun getAllSizes(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            sizeService.getAllSizes()
        }.map {
            sizeMapper.toDTOs(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun getAllColors(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            colorService.getAllColors()
        }.map {
            colorMapper.toDTOs(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun getProductSizes(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            productService.getProductSizes(it.pathVariable("product_id").toInt())
        }.map {
            sizeMapper.toDTOs(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun getProductColors(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().map {
            productService.getProductColors(it.pathVariable("product_id").toInt())
        }.map {
            colorMapper.toDTOs(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun saveProduct(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<Product>().map {
            productService.createProduct(it)
        }.map {
            productMapper.toDTO(it)
        }.flatMap {
            ok().json().body(it)
        }
}
