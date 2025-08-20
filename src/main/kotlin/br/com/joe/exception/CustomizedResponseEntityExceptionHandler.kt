package br.com.joe.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date


class CustomizedResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: Exception, request: WebRequest):
        ResponseEntity<ExceptionResponse>{
            val exceptionResponse = ExceptionResponse(
                Date(),
                ex.message,
                request.getDescription(false)
            )
            return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
        }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse>{
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

}