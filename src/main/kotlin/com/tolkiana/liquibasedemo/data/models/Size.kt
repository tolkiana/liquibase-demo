package com.tolkiana.liquibasedemo.data.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("sizes")
data class Size(
    @Id val id: Int,
    val code: String,
    val sortOrder: Number
)
