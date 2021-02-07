package com.tolkiana.liquibasedemo

import com.tolkiana.liquibasedemo.application.ProductService
import com.tolkiana.liquibasedemo.data.ProductColorRepository
import com.tolkiana.liquibasedemo.data.ProductRepository
import com.tolkiana.liquibasedemo.data.models.Color
import com.tolkiana.liquibasedemo.data.models.Product
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Collections

@SpringBootTest
class RepositoryConundrum {

    @MockBean
    private var productRepository: ProductRepository? = null

    @MockBean
    private var productColorRepository: ProductColorRepository? = null

    @Autowired
    private var productService: ProductService? = null

    @Test
    fun repositoriesAreCalledWhenExpected() {
        val color = Color(1, "code", "name")
        val product = Product(1, "code", "description", Collections.emptyList(), Collections.singletonList(color))
        Mockito.`when`(productRepository!!.save(product)).thenReturn(Mono.just(product))
        Mockito.`when`(productColorRepository!!.insertProductColors(Mockito.anyInt(), Mockito.anyList())).thenReturn(Flux.just(1))

        // when
        val response = productService!!.createProduct(product).block()

        // then
        Mockito.verify(productColorRepository)!!.insertProductColors(Mockito.anyInt(), Mockito.anyList())
    }
}