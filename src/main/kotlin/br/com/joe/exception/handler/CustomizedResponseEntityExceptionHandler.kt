package br.com.joe.exception.handler

import br.com.joe.exception.AddressNotFoundException
import br.com.joe.exception.AssociatedCategoryException
import br.com.joe.exception.CategoryNotFoundException
import br.com.joe.exception.CpfCnpjInvalidException
import br.com.joe.exception.EmailInvalidException
import br.com.joe.exception.ExceptionResponse
import br.com.joe.exception.ExistingCategoryException
import br.com.joe.exception.ExistingNumberException
import br.com.joe.exception.InsufficientStockException
import br.com.joe.exception.PedidoNotFoundException
import br.com.joe.exception.PhoneInvalidException
import br.com.joe.exception.ProductAlreadyExistsException
import br.com.joe.exception.ProductNotAvailableException
import br.com.joe.exception.ProductNotFoundException
import br.com.joe.exception.ResourceNotFoundException
import br.com.joe.exception.UserConflictException
import br.com.joe.exception.UserNotFoundException
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

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalStateException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CpfCnpjInvalidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCpfCnpjInvalidException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EmailInvalidException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleEmailInvalidException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ExistingNumberException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleExistingNumberException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AddressNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAddressNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PhoneInvalidException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlePhoneInvalidException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ExistingCategoryException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleExistingCategoryException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(CategoryNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCategoryNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProductNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ProductAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleProductAlreadyExistsException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AssociatedCategoryException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAssociatedCategoryException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ProductNotAvailableException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProductNotAvailableException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PedidoNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePedidoNouFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientStockException::class)
    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    fun handleInsufficientStockException(ex: Exception, request: WebRequest):
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INSUFFICIENT_STORAGE)
    }
}