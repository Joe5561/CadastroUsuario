package br.com.joe.entity.vo

import org.springframework.hateoas.RepresentationModel

data class UserWithVehiclesVO(
    //val user: UserVO,
    val vehicle: VehicleVO

): RepresentationModel<UserWithVehiclesVO>()
