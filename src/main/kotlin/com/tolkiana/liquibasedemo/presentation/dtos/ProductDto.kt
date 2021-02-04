package com.tolkiana.liquibasedemo.presentation.dtos

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductDto(
    val id: Number,
    val code: String? = null,
    val description: String? = null,
    val colors: Set<ColorDto>? = null,
    val size: Set<SizeDto>? = null
)
