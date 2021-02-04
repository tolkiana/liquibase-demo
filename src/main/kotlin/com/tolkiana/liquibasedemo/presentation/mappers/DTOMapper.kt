package com.tolkiana.liquibasedemo.presentation.mappers

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Interface for generic DTO mappers, use it to convert from DTOs to Domain models and vice versa
 * @param <D> The DTO class type
 * @param <M> The Domain Model class type
 */
interface DTOMapper<D, M> {
    fun toDTOs(models: Flux<M>): Flux<D>
    fun toModels(dtos: Flux<D>): Flux<M>

    fun toDTO(model: Mono<M>): Mono<D>
    fun toModel(dto: Mono<D>): Mono<M>
}
