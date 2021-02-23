package com.tolkiana.liquibasedemo.presentation.dtos

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ColorDto(
    val id: Int,
    val code: String? = null,
    val name: String? = null
)
