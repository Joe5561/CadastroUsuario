package br.com.joe.entity.dto
import org.springframework.hateoas.RepresentationModel

data class UserResponseDTO (

    var id: Long = 0,
    var name: String = "",
    var cpf: String = "",
    var email: String = "",
    var telefone: String = "",
    var address: List<AddressResponseDTO>

): RepresentationModel<UserResponseDTO>()