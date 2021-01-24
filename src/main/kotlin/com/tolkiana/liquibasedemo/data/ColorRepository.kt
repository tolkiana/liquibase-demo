package com.tolkiana.liquibasedemo.data

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ColorRepository: ReactiveCrudRepository<Color, Int> {}
