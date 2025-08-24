package br.com.joe.service

import br.com.joe.entity.Vehicle
import br.com.joe.exception.VehicleNotFoundException
import br.com.joe.repository.VehicleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VehicleService {

    @Autowired
    private lateinit var repository: VehicleRepository

    fun save(vehicle: Vehicle): Vehicle{
        return repository.save(vehicle)
    }

    fun findAllVehicle(): List<Vehicle>{
        val vehicle = repository.findAll()
        if (vehicle.isEmpty()){
            throw VehicleNotFoundException("No vehicle found!!")
        }
        return vehicle
    }

    fun findByPlaca(placa: String): Vehicle{
        val vehicle = repository.findByPlaca(placa)
        if (vehicle.placa.isEmpty()){
            throw VehicleNotFoundException("Vehicle not found for this $placa")
        }
        return vehicle
    }

    fun deleteByPlaca(placa: String): Vehicle{
        val vehicle = findByPlaca(placa)
        if (vehicle.placa.isEmpty()){
            throw VehicleNotFoundException("Vehicle not found for this $placa")
        }else{
            repository.deleteByPlaca(placa)
        }
        return vehicle
    }
}