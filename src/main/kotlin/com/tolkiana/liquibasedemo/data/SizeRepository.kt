package com.tolkiana.liquibasedemo.data

import com.tolkiana.liquibasedemo.data.mappers.SizeMapper
import com.tolkiana.liquibasedemo.data.models.Size
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

const val selectSizesByProduct = """
    SELECT sizes.id as size_id, sizes.code as size_code, sizes.sort_order as sort_order
    FROM sizes
    INNER JOIN product_sizes on sizes.id = product_sizes.size_id
    WHERE product_sizes.product_id = :productId
"""

@Repository
interface SizeRepository: ReactiveCrudRepository<Size, Int>, CustomSizeRepository {}

interface CustomSizeRepository {
    fun findByProduct(productId: Int): Flux<Size>
}

// Custom Repositories need to end with "Impl" or everything explodes!
class CustomSizeRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val mapper: SizeMapper
): CustomSizeRepository {
    override fun findByProduct(productId: Int): Flux<Size> {
        return databaseClient.sql(selectSizesByProduct)
            .bind("productId", productId)
            .map(mapper::apply)
            .all()
    }

}
