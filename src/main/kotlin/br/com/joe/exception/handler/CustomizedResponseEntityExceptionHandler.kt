package br.com.joe.exception.handler

import br.com.joe.exception.ExceptionResponse
import br.com.joe.exception.ExistingBoardException
import br.com.joe.exception.ResourceNotFoundException
import br.com.joe.exception.UserConflictException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.exception.VehicleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

@RestControllerAdvice
class CustomizedResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
            val exceptionResponse = ExceptionResponse(
                Date(),
                ex.message,
                request.getDescription(false)
            )
            return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
        }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(VehicleNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleVehicleNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ExistingBoardException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleExistingBoardException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UserConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUserConflictException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }
}