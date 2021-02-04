package com.tolkiana.liquibasedemo.data

import org.springframework.stereotype.Repository

@Repository
interface CustomProductSizeRepository {
    fun insertProductSize(productId: Number, sizeId: Number)
    fun deleteProductSize(productId: Number, sizeId: Number)
}