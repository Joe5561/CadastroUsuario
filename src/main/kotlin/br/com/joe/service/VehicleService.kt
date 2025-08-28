package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Vehicle
import br.com.joe.entity.vo.VehicleVO
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

    fun save(vehicleVO: VehicleVO): VehicleVO{
        val vehicle = mapper.toVehicle(vehicleVO)
        val saveVehicle = repository.save(vehicle)
        return mapper.toVehicleVO(saveVehicle)
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
        if (vehicle != null){
            return vehicle
        }else{
            throw VehicleNotFoundException("Vehicle not found for this $placa")
        }
    }

    fun deleteByPlaca(placa: String): Vehicle?{
        val vehicle = repository.findByPlaca(placa)
            ?: throw VehicleNotFoundException("Vehicle not found for this $placa")
        repository.delete(vehicle)
        return vehicle
    }
}