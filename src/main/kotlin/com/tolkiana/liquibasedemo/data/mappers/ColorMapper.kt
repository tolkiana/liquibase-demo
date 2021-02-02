package com.tolkiana.liquibasedemo.data.mappers

import com.tolkiana.liquibasedemo.data.models.Color
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ColorMapper: BiFunction<Row, Any, Color> {
    override fun apply(row: Row, o: Any): Color {
        return Color(
            row.get("color_id", Number::class.java)!!,
            row.get("color_code", String::class.java)!!,
            row.get("color_name", String::class.java)!!
        )
    }
}
