package com.tolkiana.liquibasedemo.data.mappers

import com.tolkiana.liquibasedemo.data.models.Size
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class SizeMapper: BiFunction<Row, Any, Size> {
    override fun apply(row: Row, o: Any): Size {
        return Size(
            row.get("size_id", Int::class.java) ?: throw UnexpectedException(),
            row.get("size_code", String::class.java) ?: throw UnexpectedException(),
            row.get("sort_order", Number::class.java) ?: throw UnexpectedException()
        )
    }
}
