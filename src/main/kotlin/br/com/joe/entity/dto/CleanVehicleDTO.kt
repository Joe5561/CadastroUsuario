package br.com.joe.entity.dto

data class CleanVehicleDTO(
    var id: Long = 0,
    var modelo: String = "",
    var marca: String = "",
    var ano: String = "",
    var placa: String = ""
)