package com.tolkiana.liquibasedemo.data.models

import org.springframework.data.relational.core.mapping.Table

@Table("sizes")
data class Size(
    val id: Number,
    val code: String,
    val sortOrder: Number
)
