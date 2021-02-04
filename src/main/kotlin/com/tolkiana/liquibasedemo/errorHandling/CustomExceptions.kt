package com.tolkiana.liquibasedemo.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class DuplicatedResourceException(message: String = "Resource already exists") : ResponseStatusException(
    HttpStatus.CONFLICT,
    message
)

class ResourceNotFoundException(message: String = "Resource doesn't exist") : ResponseStatusException(
    HttpStatus.NOT_FOUND,
    message
)

class BadRequestException(message: String = "Invalid request") : ResponseStatusException(
    HttpStatus.BAD_REQUEST,
    message
)

class UnexpectedException(message: String = "An unexpected error happen, please try again later") : ResponseStatusException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    message
)
