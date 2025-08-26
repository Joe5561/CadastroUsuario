package br.com.joe.controllers

import br.com.joe.entity.Vehicle
import br.com.joe.service.VehicleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/vehicle")
@Tag(name = "Vehicle", description = "Operações relacionadas a veiculos")
class VehicleController {

    @Autowired
    private lateinit var service: VehicleService

    @PostMapping
    @Operation(summary = "Cadastrar veiculos", description = "Efetuar o cadastro de veiculos")
    fun saveVehicle(@RequestBody vehicle: Vehicle): ResponseEntity<Vehicle>{
        val vehicleSave = service.save(vehicle)
        return ResponseEntity.ok().body(vehicleSave)
    }

    @GetMapping
    @Operation(summary = "Buscar todos os veículos")
    fun listVehicle(): ResponseEntity<List<Vehicle>>{
        val vehicles = service.findAllVehicle()
        return ResponseEntity.ok().body(vehicles)
    }

    @GetMapping("/placa")
    @Operation(summary = "Buscar pela placa")
    fun findByPlaca(@RequestParam placa: String): Vehicle{
        return service.findByPlaca(placa)
    }

    @DeleteMapping("/deletePlaca")
    @Operation(summary = "Deletar veículo pela placa")
    fun deleteByPlaca(@RequestParam placa: String): ResponseEntity<Vehicle>{
        val deleteVehicle = service.deleteByPlaca(placa)
        return ResponseEntity.ok().body(deleteVehicle)
    }
}