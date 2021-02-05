package com.tolkiana.liquibasedemo.presentation.mappers

import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.presentation.dtos.ColorDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ColorDTOMapper: DTOMapper<ColorDto, Color> {
    override fun toDTOs(models: Flux<Color>): Flux<ColorDto> {
        return models.map { toDTO(it) }
    }

    override fun toModels(dtos: Flux<ColorDto>): Flux<Color> {
        return dtos.map { toModel(it) }
    }

    override fun toDTO(model: Mono<Color>): Mono<ColorDto> {
        return model.map { toDTO(it) }
    }

    override fun toModel(dto: Mono<ColorDto>): Mono<Color> {
        return dto.map { toModel(it) }
    }

    fun toDTO(model: Color): ColorDto {
        return ColorDto(model.id, model.code, model.name)
    }

    fun toModel(dto: ColorDto): Color {
        return Color(dto.id, dto.code ?: "", dto.name ?: "")
    }
}
