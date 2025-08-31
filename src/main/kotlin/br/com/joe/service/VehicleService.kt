package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Vehicle
import br.com.joe.entity.dto.VehicleSummaryDTO
import br.com.joe.entity.vo.VehicleVO
import br.com.joe.exception.ExistingBoardException
import br.com.joe.exception.VehicleNotFoundException
import br.com.joe.repository.VehicleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VehicleService {

    @Autowired
    private lateinit var repository: VehicleRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    fun save(vehicleDTO: VehicleSummaryDTO): VehicleSummaryDTO{
        val existingVehicle = repository.findByPlaca(vehicleDTO.placa)
        if (existingVehicle != null){
            throw ExistingBoardException("This board is already registered!! ${existingVehicle.placa}")
        }
        val vehicle = Vehicle(
            modelo = vehicleDTO.modelo,
            marca = vehicleDTO.marca,
            ano = vehicleDTO.ano,
            placa = vehicleDTO.placa,
            //user = null
        )
        val saveVehicle = repository.save(vehicle)
        return VehicleSummaryDTO(
            id = saveVehicle.id,
            modelo = saveVehicle.modelo,
            marca = saveVehicle.marca,
            ano = saveVehicle.ano,
            placa = saveVehicle.placa
        )
    }

    fun findAllVehicle(): List<VehicleSummaryDTO>{
        val vehicle = repository.findAll()
        if (vehicle.isEmpty()){
            throw VehicleNotFoundException("No vehicles found!!")
        }
        return mapper.toVehicleSummaryDTOList(vehicle)
    }

    fun findByPlaca(placa: String): VehicleSummaryDTO{
        val vehicle = repository.findByPlaca(placa)
            ?: throw VehicleNotFoundException("Vehicle not found for this $placa")
        return VehicleSummaryDTO(
            id = vehicle.id,
            modelo = vehicle.modelo,
            marca = vehicle.marca,
            ano = vehicle.ano,
            placa = vehicle.placa
        )
    }

    fun deleteByPlaca(placa: String): VehicleSummaryDTO{
        if (!repository.existsByPlaca(placa)){
            throw VehicleNotFoundException("Vehicle not found for placa: $placa")
        }
        val vehicle = repository.findByPlaca(placa)!!
        repository.delete(vehicle)

        return VehicleSummaryDTO(
            id = vehicle.id,
            modelo = vehicle.modelo,
            marca = vehicle.marca,
            ano = vehicle.ano,
            placa = vehicle.placa
        )
    }
}