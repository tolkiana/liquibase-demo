package com.tolkiana.liquibasedemo.data

import com.tolkiana.liquibasedemo.data.mappers.ProductMapper
import com.tolkiana.liquibasedemo.data.models.Product
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

const val selectAllProducts = """
    SELECT p.id as product_id, p.code as product_code, description as product_description
    FROM products p;
"""

const val insertProduct = """
    INSERT INTO products (code, description) VALUES (:code, :description)
"""

const val insertProductColor = """
    INSERT INTO product_colors (product_id, color_id) VALUES ($1, $2)
"""

const val insertProductSize = """
    INSERT INTO product_sizes (product_id, size_id) VALUES ($1, $2)
"""

const val deleteAllProductColors = """
    DELETE FROM product_colors WHERE product_id = :productId
"""

const val deleteAllProductSizes = """
    DELETE FROM product_sizes WHERE product_id = :productId
"""

const val updateProduct = """
    UPDATE products SET code = :code, description = :description WHERE id = :productId
"""

const val deleteProductColor = """
    DELETE FROM product_colors WHERE product_id = $1 AND color_id = $2
"""

const val deleteProductSize = """
    DELETE FROM product_sizes WHERE product_id = $1 AND size_id = $2
"""

interface ProductRepository: ReactiveCrudRepository<Product, Int>, CustomProductRepository {}

interface CustomProductRepository {
    fun findAll(): Flux<Product>
    fun save(product: Product): Mono<Product>
    fun update(product: Product): Mono<Int>
    fun insertProductColors(productId: Int, colorIds: List<Int>): Flux<Number>
    fun insertProductSizes(productId: Int, sizeIds: List<Int>): Flux<Number>
    fun deleteAllProductColors(productId: Int): Mono<Int>
    fun deleteAllProductSizes(productId: Int): Mono<Int>
    fun deleteProductColors(productId: Int, colorIds: List<Int>): Mono<Void>
    fun deleteProductSizes(productId: Int, sizesIds: List<Int>): Mono<Void>
}

// Custom Repositories need to end with "Impl" or everything explodes!
class CustomProductRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val mapper: ProductMapper
): CustomProductRepository {
    override fun findAll(): Flux<Product> {
        return databaseClient.sql(selectAllProducts)
            .map(mapper::apply)
            .all()
    }

    override fun save(product: Product): Mono<Product> {
        return databaseClient.sql(insertProduct)
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("code", product.code)
            .bind("description", product.description)
            .fetch()
            .first()
            .map { product.copy(id = it["id"] as Int) }
    }

    override fun update(product: Product): Mono<Int> {
        return databaseClient.sql(updateProduct)
            .bind("code", product.code)
            .bind("description", product.description)
            .bind("productId", product.id)
            .fetch().rowsUpdated()
    }

    override fun insertProductColors(productId: Int, colorIds: List<Int>): Flux<Number> {
        if (colorIds.isEmpty()) return Flux.empty()
        return databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement(insertProductColor)
            colorIds.forEach {
                statement.bind(0, productId).bind(1, it).add()
            }
            statement.execute().toFlux().flatMap { result ->
                result.map { row, _ -> row.get("color_id", Int::class.java) }
            }
        }
    }

    override fun insertProductSizes(productId: Int, sizeIds: List<Int>): Flux<Number> {
        if (sizeIds.isEmpty()) return Flux.empty()
        return databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement(insertProductSize)
            sizeIds.forEach {
                statement.bind(0, productId).bind(1, it).add()
            }
            statement.execute().toFlux().flatMap { result ->
                result.map { row, _ -> row.get("size_id", Int::class.java) }
            }
        }
    }

    override fun deleteAllProductColors(productId: Int): Mono<Int> {
        return databaseClient
            .sql(deleteAllProductColors)
            .bind("productId", productId)
            .fetch().rowsUpdated()
    }

    override fun deleteAllProductSizes(productId: Int): Mono<Int> {
        return databaseClient
                .sql(deleteAllProductSizes)
                .bind("productId", productId)
                .fetch().rowsUpdated()
    }

    override fun deleteProductColors(productId: Int, colorIds: List<Int>): Mono<Void> {
        if (colorIds.isEmpty()) return  Mono.empty()
        return databaseClient.inConnection { connection ->
            val statement = connection.createStatement(deleteProductColor)
            colorIds.forEach {
                statement.bind(0, productId).bind(1, it).add()
            }
            statement.execute().toFlux().then()
        }
    }

    override fun deleteProductSizes(productId: Int, sizesIds: List<Int>): Mono<Void> {
        if (sizesIds.isEmpty()) return  Mono.empty()
        return databaseClient.inConnection { connection ->
            val statement = connection.createStatement(deleteProductSize)
            sizesIds.forEach {
                statement.bind(0, productId).bind(1, it).add()
            }
            statement.execute().toFlux().then()
        }
    }
}
