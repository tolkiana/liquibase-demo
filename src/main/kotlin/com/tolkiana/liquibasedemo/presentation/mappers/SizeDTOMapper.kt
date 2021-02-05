package com.tolkiana.liquibasedemo.presentation.mappers

import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.presentation.dtos.SizeDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class SizeMapper: DTOMapper<SizeDto, Size> {
    override fun toDTOs(models: Flux<Size>): Flux<SizeDto> {
        return models.map { toDTO(it) }
    }

    override fun toModels(dtos: Flux<SizeDto>): Flux<Size> {
        return dtos.map { toModel(it) }
    }

    override fun toDTO(model: Mono<Size>): Mono<SizeDto> {
        return model.map { toDTO(it) }
    }

    override fun toModel(dto: Mono<SizeDto>): Mono<Size> {
        return dto.map { toModel(it) }
    }

    fun toDTO(model: Size): SizeDto {
        return SizeDto(model.id, model.code, model.sortOrder)
    }

    fun toModel(dto: SizeDto): Size {
        return Size(dto.id, dto.code ?: "", dto.sortOrder ?: 0)
    }
}
