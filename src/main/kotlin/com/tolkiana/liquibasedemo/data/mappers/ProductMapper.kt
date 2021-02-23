package com.tolkiana.liquibasedemo.data.mappers

import com.tolkiana.liquibasedemo.data.models.Product
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ProductMapper: BiFunction<Row, Any, Product> {
    override fun apply(row: Row, o: Any): Product {
        return Product(
            row.get("product_id", Number::class.java)?.toInt() ?: throw UnexpectedException(),
            row.get("product_code", String::class.java) ?: throw UnexpectedException(),
            row.get("product_description", String::class.java) ?: throw UnexpectedException()
        )
    }
}
