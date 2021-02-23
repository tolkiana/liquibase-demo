package com.tolkiana.liquibasedemo.data.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("colors")
data class Color(
    @Id val id: Int,
    val code: String,
    val name: String
)
