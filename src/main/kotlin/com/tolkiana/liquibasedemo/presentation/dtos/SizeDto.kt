package com.tolkiana.liquibasedemo.presentation.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import javax.swing.SortOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SizeDto(
    val id: Int,
    val code: String? = null,
    @JsonProperty("sort_order")
    val sortOrder: Number? = null
)
