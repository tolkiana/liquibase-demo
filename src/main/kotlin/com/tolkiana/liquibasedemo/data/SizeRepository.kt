package com.tolkiana.liquibasedemo.data

import com.tolkiana.liquibasedemo.data.mappers.SizeMapper
import com.tolkiana.liquibasedemo.data.models.Size
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.repository.reactive.ReactiveCrudRepository
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
    fun findByProduct(productId: Number): Flux<Size>
}

// Custom Repositories need to en with "Impl" or everything explodes!
class CustomSizeRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val mapper: SizeMapper
): CustomSizeRepository {
    override fun findByProduct(productId: Number): Flux<Size> {
        return databaseClient
            .execute(selectSizesByProduct)
            .bind("productId", productId)
            .map(mapper::apply)
            .all()
    }

}
