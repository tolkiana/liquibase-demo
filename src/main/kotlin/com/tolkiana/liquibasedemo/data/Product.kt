package com.tolkiana.liquibasedemo.data

import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    val id: Int,
    val name: String,
    val description: String
)