package br.com.joe.utils.annotation

import br.com.joe.utils.validator.CpfCnpjValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CpfCnpjValidator::class])
annotation class CpfCnpj(

    val message: String = "CPF ou CNPJ inválido",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []

)