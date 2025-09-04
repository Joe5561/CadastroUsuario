package br.com.joe.entity.vo

import br.com.joe.entity.dto.CleanVehicleDTO
import org.springframework.hateoas.RepresentationModel

data class UserWithCleanVehiclesVO(

    val vehicles: List<CleanVehicleDTO>,
    val user: UserVO
): RepresentationModel<UserWithCleanVehiclesVO>()
