package com.tolkiana.liquibasedemo.application

import com.tolkiana.liquibasedemo.data.SizeRepository
import com.tolkiana.liquibasedemo.data.models.Size
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SizeService(private val sizeRepository: SizeRepository) {

    fun getAllSizes(): Flux<Size> {
        return sizeRepository.findAll()
    }
}
