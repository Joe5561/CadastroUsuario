package br.com.joe.controllers

import br.com.joe.entity.vo.UserWithVehiclesVO
import br.com.joe.entity.vo.VehicleVO
import br.com.joe.service.UserVehicleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hire")
@Tag(name = "Users and Vehicles", description = "Alugar veículos")
class UserVehicleController {

    @Autowired
    private lateinit var userVehicleService: UserVehicleService

    @PostMapping("/vehicles")
    @Operation(summary = "Rent vehicles")
    fun saveUserAndVehicle(@RequestParam cpf: String, @RequestParam placa: String): ResponseEntity<VehicleVO>{
        val vehicleVO = userVehicleService.saveUserAndVehicle(cpf, placa)
        vehicleVO.add(
            linkTo(methodOn(UserVehicleController::class.java)
                .saveUserAndVehicle(cpf, placa)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleVO)
    }

    @GetMapping("userWhithVehicle")
    @Operation(summary = "Search for rented vehicles")
    fun findUserWithVehicle(@RequestParam cpf: String): ResponseEntity<UserWithVehiclesVO>{
        val userVehicles = userVehicleService.findUserWithVehicleByCpf(cpf)
        userVehicles.add(
            linkTo(methodOn(UserVehicleController::class.java)
                .findUserWithVehicle(cpf)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userVehicles)
    }
}