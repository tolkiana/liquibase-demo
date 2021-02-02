package com.tolkiana.liquibasedemo.data.models

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.relational.core.mapping.Table

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table("products")
data class Product(
    val id: Number,
    val code: String,
    val description: String,
    val sizes: List<Size>,
    val colors: List<Color>
)