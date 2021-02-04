package com.tolkiana.liquibasedemo.data.models

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.relational.core.mapping.Table

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table("products")
data class Product(
    val id: Number ? = null,
    val code: String,
    val description: String,
    val sizes: List<Size> ? = null,
    val colors: List<Color> ? = null
)