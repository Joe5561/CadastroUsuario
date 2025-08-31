package br.com.joe.controllers

import br.com.joe.entity.dto.VehicleSummaryDTO
import br.com.joe.entity.vo.VehicleVO
import br.com.joe.service.VehicleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
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
    fun saveVehicleDTO(@RequestBody vehicleDTO: VehicleSummaryDTO): ResponseEntity<EntityModel<VehicleSummaryDTO>>{
        val saveVehicleDTO = service.save(vehicleDTO)

        val resource = EntityModel.of(saveVehicleDTO).add(
            linkTo(
                methodOn(VehicleController::class.java)
                    .findByPlaca(saveVehicleDTO.placa)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(resource)
    }

    @GetMapping
    @Operation(summary = "Buscar todos os veículos")
    fun listVehicle(): ResponseEntity<List<EntityModel<VehicleSummaryDTO>>>{
        val vehicles = service.findAllVehicle()
        val vehicleResources = vehicles.map { dTO ->
            EntityModel.of(dTO).add(
                linkTo(
                    methodOn(VehicleController::class.java)
                        .findByPlaca(dTO.placa)).withSelfRel()
                )
        }
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResources)
    }

    @GetMapping("/placa")
    @Operation(summary = "Buscar pela placa")
    fun findByPlaca(@RequestParam placa: String): ResponseEntity<EntityModel<VehicleSummaryDTO>>    {
        val vehicleDTO = service.findByPlaca(placa)
        val resource = EntityModel.of(vehicleDTO).add(
            linkTo(
                methodOn(VehicleController::class.java)
                    .findByPlaca(placa)).withSelfRel()
            )
        return ResponseEntity.status(HttpStatus.OK).body(resource)
    }

    @DeleteMapping("/deletePlaca")
    @Operation(summary = "Deletar veículo pela placa")
    fun deleteByPlaca(@RequestParam placa: String): ResponseEntity<EntityModel<VehicleSummaryDTO>>{
        val deleteVehicleDTO = service.deleteByPlaca(placa)

        val resource = EntityModel.of(deleteVehicleDTO).add(
            linkTo(
                methodOn(VehicleController::class.java)
                    .findByPlaca(placa)).withSelfRel()
            )
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(resource)
        }
}