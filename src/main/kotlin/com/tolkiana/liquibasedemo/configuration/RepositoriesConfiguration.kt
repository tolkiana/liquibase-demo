package com.tolkiana.liquibasedemo.configuration

import com.tolkiana.liquibasedemo.data.DefaultProductColorRepository
import com.tolkiana.liquibasedemo.data.DefaultProductSizeRepository
import com.tolkiana.liquibasedemo.data.ProductColorRepository
import com.tolkiana.liquibasedemo.data.ProductSizeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class RepositoriesConfiguration  {

    @Bean
    fun productColorRepository(databaseClient: DatabaseClient): ProductColorRepository {
        return DefaultProductColorRepository(databaseClient)
    }

    fun productSizeRepository(databaseClient: DatabaseClient): ProductSizeRepository {
        return DefaultProductSizeRepository(databaseClient)
    }
}
