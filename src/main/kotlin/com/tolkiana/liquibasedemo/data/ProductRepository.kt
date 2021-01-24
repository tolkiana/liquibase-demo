package com.tolkiana.liquibasedemo.data

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: ReactiveCrudRepository<Product, Int> {}