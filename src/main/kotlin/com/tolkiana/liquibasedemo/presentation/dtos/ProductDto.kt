package com.tolkiana.liquibasedemo.presentation.dtos

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ProductDto(
    val id: Int? = null,
    val code: String? = null,
    val description: String? = null,
    val colors: Set<ColorDto>? = null,
    val sizes: Set<SizeDto>? = null
)
