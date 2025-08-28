package br.com.joe.controllers

import br.com.joe.entity.vo.VehicleVO
import br.com.joe.service.VehicleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn

@RestController
@RequestMapping("/vehicle")
@Tag(name = "Vehicle", description = "Operações relacionadas a veiculos")
class VehicleController {

    @Autowired
    private lateinit var service: VehicleService

    @PostMapping
    @Operation(summary = "Cadastrar veiculos", description = "Efetuar o cadastro de veiculos")
    fun saveVehicle(@RequestBody vehicleVO: VehicleVO): ResponseEntity<VehicleVO>{
        val saveVehicleVO = service.save(vehicleVO)
        saveVehicleVO.add(
            linkTo(methodOn(VehicleController::class.java)
                .saveVehicle(saveVehicleVO)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(saveVehicleVO)
    }

    @GetMapping
    @Operation(summary = "Buscar todos os veículos")
    fun listVehicle(): ResponseEntity<List<VehicleVO>>{
        val vehicles = service.findAllVehicle()
        vehicles.forEach { vehicleVO ->
            vehicleVO.add(
                linkTo(methodOn(VehicleController::class.java)
                    .findByPlaca(vehicleVO.placa)).withSelfRel()
            )
        }
        return ResponseEntity.ok().body(vehicles)
    }

    @GetMapping("/placa")
    @Operation(summary = "Buscar pela placa")
    fun findByPlaca(@RequestParam placa: String): ResponseEntity<VehicleVO>{
        val vehicleVO = service.findByPlaca(placa)
        vehicleVO.add(
            linkTo(methodOn(VehicleController::class.java)
                .findByPlaca(placa)).withSelfRel()
        )
        return ResponseEntity.ok(vehicleVO)
    }

    @DeleteMapping("/deletePlaca")
    @Operation(summary = "Deletar veículo pela placa")
    fun deleteByPlaca(@RequestParam placa: String): ResponseEntity<VehicleVO>{
        val deleteVehicleVO = service.deleteByPlaca(placa)
        deleteVehicleVO.add(
            linkTo(methodOn(VehicleController::class.java)
                .findByPlaca(placa)).withSelfRel()
        )
        return ResponseEntity.ok().body(deleteVehicleVO)
    }
}