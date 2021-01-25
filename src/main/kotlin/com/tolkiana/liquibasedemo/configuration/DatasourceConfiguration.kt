package com.tolkiana.liquibasedemo.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ConfigurationProperties("spring.datasource")
class DatasourceConfiguration {
    lateinit var driverClassName: String
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
    @Bean
    fun getDataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName(driverClassName)
        dataSourceBuilder.url(url)
        dataSourceBuilder.username(username)
        dataSourceBuilder.password(password)
        return dataSourceBuilder.build()
    }
}
