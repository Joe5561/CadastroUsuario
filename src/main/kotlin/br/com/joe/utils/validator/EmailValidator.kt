package br.com.joe.utils.validator

class EmailValidator {
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        return Regex(emailRegex).matches(email)
    }
}