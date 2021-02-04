package com.tolkiana.liquibasedemo.presentation.mappers

import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import com.tolkiana.liquibasedemo.presentation.dtos.ProductDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductMapper(
    private val colorMapper: ColorMapper,
    private val sizeMapper: SizeMapper
): DTOMapper<ProductDto, Product> {
    override fun toDTOs(models: Flux<Product>): Flux<ProductDto> {
        return models.map { toDTO(it) }
    }

    override fun toModels(dtos: Flux<ProductDto>): Flux<Product> {
        return dtos.map { toModel(it) }
    }

    override fun toDTO(model: Mono<Product>): Mono<ProductDto> {
        return model.map { toDTO(it) }
    }

    override fun toModel(dto: Mono<ProductDto>): Mono<Product> {
        return dto.map { toModel(it) }
    }

    fun toDTO(model: Product): ProductDto {
        return ProductDto(
            id = model.id ?: throw UnexpectedException(),
            code = model.code,
            description = model.description,
            colors = model.colors.map { colorMapper.toDTO(it) }.toSet(),
            size = model.sizes.map { sizeMapper.toDTO(it) }.toSet()
        )
    }

    fun toModel(dto: ProductDto): Product {
        return Product(
            id = dto.id,
            code = dto.code ?: "",
            description = dto.description ?: "",
            colors = dto.colors?.map { colorMapper.toModel(it) } ?: emptyList(),
            sizes = dto.size?.map { sizeMapper.toModel(it) } ?: emptyList()
        )
    }
}
