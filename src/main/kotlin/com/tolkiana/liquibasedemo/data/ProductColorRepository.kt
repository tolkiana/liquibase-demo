package com.tolkiana.liquibasedemo.data

import org.springframework.stereotype.Repository

@Repository
interface CustomProductColorRepository {
    fun insertProductColor(productId: Number, colorId: Number)
    fun deleteProductColor(productId: Number, colorId: Number)
}
