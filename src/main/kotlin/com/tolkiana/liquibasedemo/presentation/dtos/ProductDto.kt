package com.tolkiana.liquibasedemo.presentation.dtos

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductDto(
    val id: Number? = null,
    val code: String? = null,
    val description: String? = null,
    val colors: Set<ColorDto>? = null,
    val sizes: Set<SizeDto>? = null
)
