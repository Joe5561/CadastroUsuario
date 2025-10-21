package br.com.joe.exception
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
class InsufficientStockException : RuntimeException {
    val produtosComErro: List<String>?
    constructor(message: String) : super(message) {
        this.produtosComErro = null    }

    constructor(produtosComErro: List<String>) :
            super("Estoque insuficiente para os produtos: ${produtosComErro.joinToString(", ")}") {
        this.produtosComErro = produtosComErro
    }
}
