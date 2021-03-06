package com.tolkiana.liquibasedemo.data

import com.tolkiana.liquibasedemo.data.mappers.ColorMapper
import com.tolkiana.liquibasedemo.data.models.Color
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

const val selectColorsByProduct = """
    SELECT colors.id as color_id, colors.code as color_code, colors.name as color_name
    FROM colors
    INNER JOIN product_colors pc on colors.id = pc.color_id
    WHERE product_id = :productId
"""

@Repository
interface ColorRepository: ReactiveCrudRepository<Color, Int>, CustomColorRepository {}

interface CustomColorRepository {
    fun findByProduct(productId: Int): Flux<Color>
}

// Custom Repositories need to end with "Impl" or everything explodes!
class CustomColorRepositoryImpl(
        private val databaseClient: DatabaseClient,
        private val mapper: ColorMapper
): CustomColorRepository {
    override fun findByProduct(productId: Int): Flux<Color> {
        return databaseClient.sql(selectColorsByProduct)
            .bind("productId", productId)
            .map(mapper::apply)
            .all()
    }
}
