package com.tolkiana.liquibasedemo.data.models

import org.springframework.data.relational.core.mapping.Table

@Table("colors")
data class Color(
    val id: Number,
    val code: String,
    val name: String
)
