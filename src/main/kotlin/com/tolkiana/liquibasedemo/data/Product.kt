package com.tolkiana.liquibasedemo.data

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val size: ProductSize
)