package com.tolkiana.liquibasedemo.data

import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

const val insertProductColor = """
    INSERT INTO product_colors (product_id, color_id) VALUES (:productId, colorId)
"""

const val deleteProductColor = """
    DELETE FROM product_colors WHERE color_id = :colorId AND product_id = :productId
"""

@Repository
interface ProductColorRepository {
    fun insertProductColor(productId: Number, colorId: Number)
    fun deleteProductColor(productId: Number, colorId: Number)
}

class DefaultProductColorRepository(private val databaseClient: DatabaseClient): ProductColorRepository {
    override fun insertProductColor(productId: Number, colorId: Number) {
        databaseClient.execute(insertProductColor)
            .bind("productId", productId)
            .bind("colorId", colorId)
            .fetch()
            .first()
    }

    override fun deleteProductColor(productId: Number, colorId: Number) {
        databaseClient.execute(deleteProductColor)
            .bind("productId", productId)
            .bind("colorId", colorId).fetch()
            .rowsUpdated()
    }
}
