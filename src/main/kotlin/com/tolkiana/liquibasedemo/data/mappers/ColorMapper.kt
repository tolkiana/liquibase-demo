package com.tolkiana.liquibasedemo.data.mappers

import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.errorHandling.UnexpectedException
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ColorMapper: BiFunction<Row, Any, Color> {
    override fun apply(row: Row, o: Any): Color {
        return Color(
            row.get("color_id", Int::class.java) ?: throw UnexpectedException(),
            row.get("color_code", String::class.java) ?: throw UnexpectedException(),
            row.get("color_name", String::class.java) ?: throw UnexpectedException()
        )
    }
}
