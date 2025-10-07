package br.com.joe.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.CONFLICT)
class PhoneInvalidException(exception: String): RuntimeException(exception) {
}