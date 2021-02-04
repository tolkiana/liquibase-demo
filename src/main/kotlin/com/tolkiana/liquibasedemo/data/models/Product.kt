package com.tolkiana.liquibasedemo.data.models

import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    val id: Number? = null,
    val code: String,
    val description: String,
    val sizes: List<Size> = emptyList(),
    val colors: List<Color> = emptyList()
)