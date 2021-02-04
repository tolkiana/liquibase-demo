package com.tolkiana.liquibasedemo.application

import com.tolkiana.liquibasedemo.data.ColorRepository
import com.tolkiana.liquibasedemo.data.models.Color
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ColorService(private val colorRepository: ColorRepository) {

    fun getAllColors(): Flux<Color> {
        return colorRepository.findAll()
    }
}
