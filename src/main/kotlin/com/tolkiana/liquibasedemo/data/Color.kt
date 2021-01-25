package com.tolkiana.liquibasedemo.data

import org.springframework.data.relational.core.mapping.Table

@Table("colors")
data class Color(
    val id: Int,
    val code: String,
    val name: String
)
