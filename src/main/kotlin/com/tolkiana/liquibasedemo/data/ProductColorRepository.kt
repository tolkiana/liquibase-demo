package com.tolkiana.liquibasedemo.data

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux


const val insertProductColor = """
    INSERT INTO product_colors (product_id, color_id) VALUES ($1, $2)
"""

const val deleteProductColor = """
    DELETE FROM product_colors WHERE color_id = :colorId AND product_id = :productId
"""

@Repository
interface ProductColorRepository {
    fun insertProductColors(productId: Number, colorIds: List<Number>): Flux<Number>
    fun deleteProductColors(productId: Number, colorIds: List<Number>)
}

class DefaultProductColorRepository(private val databaseClient: DatabaseClient) : ProductColorRepository {
    override fun insertProductColors(productId: Number, colorIds: List<Number>): Flux<Number> {
        return databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement(insertProductColor)
            colorIds.forEach {
                statement.bind(0, productId).bind(1, it).add()
            }
            statement.execute().toFlux().flatMap { result ->
                result.map { row, _ -> row.get("color_id", Number::class.java)!! }
            }
        }
    }

    override fun deleteProductColors(productId: Number, colorIds: List<Number>) {
        TODO("Not yet implemented")
    }

}
