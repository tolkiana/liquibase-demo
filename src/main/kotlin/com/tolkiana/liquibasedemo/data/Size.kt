package com.tolkiana.liquibasedemo.data

import org.springframework.data.relational.core.mapping.Table

@Table("sizes")
data class Size(
    val id: Int,
    val code: String,
    val sortOrder: Int
)
