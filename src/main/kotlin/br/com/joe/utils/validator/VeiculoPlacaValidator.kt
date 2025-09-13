package br.com.joe.utils.validator

class VeiculoPlacaValidator {

    fun isValidPlaca(placa: String): Boolean {
        val placaLimpa = placa.replace("-", "").uppercase()

        val regexAntigo = Regex("^[A-Z]{3}[0-9]{4}$")
        val regexMercosul = Regex("^[A-Z]{3}[0-9][A-Z][0-9]{2}$")
        return regexAntigo.matches(placaLimpa) || regexMercosul.matches(placaLimpa)
    }
}