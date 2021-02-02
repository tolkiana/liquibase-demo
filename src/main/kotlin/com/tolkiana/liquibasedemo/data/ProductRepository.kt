package com.tolkiana.liquibasedemo.data

import com.tolkiana.liquibasedemo.data.mappers.ProductMapper
import com.tolkiana.liquibasedemo.data.models.Product
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

const val selectAllProducts = """
    SELECT p.id as product_id, p.code as product_code, description as product_description
    FROM products p;
"""

interface ProductRepository: ReactiveCrudRepository<Product, Int>, CustomProductRepository {}

interface CustomProductRepository {
    fun findAll(): Flux<Product>
}

// This has to be named CustomProductRepositoryImpl or everything explodes!
class CustomProductRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val mapper: ProductMapper
): CustomProductRepository {
    override fun findAll(): Flux<Product> {
        return databaseClient
            .execute(selectAllProducts)
            .map(mapper::apply)
            .all()
    }
}
