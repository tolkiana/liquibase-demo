package com.tolkiana.liquibasedemo.data

import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface ProductSizeRepository {
    fun insertProductSize(productId: Number, sizeId: Number)
    fun deleteProductSize(productId: Number, sizeId: Number)
}

class DefaultProductSizeRepository(private val databaseClient: DatabaseClient): ProductSizeRepository {
    override fun insertProductSize(productId: Number, sizeId: Number) {
        TODO("Not yet implemented")
    }

    override fun deleteProductSize(productId: Number, sizeId: Number) {
        TODO("Not yet implemented")
    }

}
