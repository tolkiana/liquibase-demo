package com.tolkiana.liquibasedemo.data.mappers

import com.tolkiana.liquibasedemo.data.models.Product
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ProductMapper: BiFunction<Row, Any, Product> {
    override fun apply(row: Row, o: Any): Product {
        return Product(
            row.get("product_id", Number::class.java)!!,
            row.get("product_code", String::class.java)!!,
            row.get("product_description", String::class.java)!!,
            emptyList(),
            emptyList()
        )
    }
}
