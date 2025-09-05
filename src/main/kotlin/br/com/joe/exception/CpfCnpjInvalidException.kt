package br.com.joe.exception

import java.lang.RuntimeException

class CpfCnpjInvalidException(exception: String): RuntimeException(exception) {
}