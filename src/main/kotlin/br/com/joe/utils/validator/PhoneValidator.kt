package br.com.joe.utils.validator

class PhoneValidator {
    fun isPhoneValid(phone: String): Boolean {
        val phoneRegex = "^\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$"
        return Regex(phoneRegex).matches(phone)
    }

    fun isPhone(phone: String): Boolean {
        val phoneRegex = "^\\d{11}$"
        return Regex(phoneRegex).matches(phone)
    }
}