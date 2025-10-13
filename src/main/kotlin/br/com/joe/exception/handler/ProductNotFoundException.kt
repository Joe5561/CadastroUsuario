package br.com.joe.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException(exception: String): RuntimeException(exception) {
}