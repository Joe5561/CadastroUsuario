package br.com.joe.entity.vo

import org.springframework.hateoas.RepresentationModel

data class UserWithCleanVehiclesVO(

    val vehicles: List<CleanVehicleVO>,
    val user: UserVO
): RepresentationModel<UserWithCleanVehiclesVO>()
