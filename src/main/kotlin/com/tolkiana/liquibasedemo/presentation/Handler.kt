package com.tolkiana.liquibasedemo.presentation

import com.tolkiana.liquibasedemo.application.ColorService
import com.tolkiana.liquibasedemo.application.ProductService
import com.tolkiana.liquibasedemo.application.SizeService
import com.tolkiana.liquibasedemo.presentation.dtos.ProductDto
import com.tolkiana.liquibasedemo.presentation.mappers.ColorDTOMapper
import com.tolkiana.liquibasedemo.presentation.mappers.ProductDTOMapper
import com.tolkiana.liquibasedemo.presentation.mappers.SizeDTOMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.noContent
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class Handler(
    private val sizeService: SizeService,
    private val colorService: ColorService,
    private val productService: ProductService,
    private val colorMapper: ColorDTOMapper,
    private val sizeMapper: SizeDTOMapper,
    private val productMapper: ProductDTOMapper
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
        request.bodyToMono<ProductDto>().map {
            productMapper.toModel(it)
        }.map {
            productService.createProduct(it)
        }.map {
            productMapper.toDTO(it)
        }.flatMap {
            ok().json().body(it)
        }

    fun deleteProduct(request: ServerRequest): Mono<ServerResponse> =
        request.toMono().flatMap {
            productService.deleteProduct(it.pathVariable("product_id").toInt())
        }.flatMap {
            noContent().build()
        }
}
