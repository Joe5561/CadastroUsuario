package br.com.joe.utils.validator

import br.com.joe.utils.annotation.CpfCnpj
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CpfCnpjValidator: ConstraintValidator<CpfCnpj, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrBlank()) return false
        val doc = value.replace(Regex("[^\\d]"), "")
        return isValidCpf(doc) || isValidCnpj(doc)
    }

    fun isValidCpf(cpf: String): Boolean{
        if (cpf.length != 11 || cpf.matches(Regex("(\\d)\\1{10}"))) return false
        val digits = cpf.map { it.toString().toInt() }
        val dv1 = ((0..8).sumOf { (10 - it) * digits[it] } * 10 % 11).let { if (it == 10) 0 else it }
        val dv2 = ((0..9).sumOf { (11 - it) * digits[it] } * 10 % 11).let { if (it == 10) 0 else it }
        return digits[9] == dv1 && digits[10] == dv2
    }

    fun isValidCnpj(cnpj: String): Boolean{
        if (cnpj.length != 14 || cnpj.matches(Regex("(\\d)\\1{13}"))) return false
        val digits = cnpj.map { it.toString().toInt() }
        val weights1 = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
        val weights2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
        val dv1 = (0..11).sumOf { digits[it] * weights1[it] } % 11.let { if (it < 2) 0 else 11 - it }
        val dv2 = (0..12).sumOf { digits[it] * weights2[it] } % 11.let { if (it < 2) 0 else 11 - it }
        return digits[12] == dv1 && digits[13] == dv2
    }
}