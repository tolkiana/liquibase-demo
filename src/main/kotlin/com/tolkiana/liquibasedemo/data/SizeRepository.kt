package com.tolkiana.liquibasedemo.data

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface SizeRepository: ReactiveCrudRepository<Size, Int> {}
