package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
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

    fun save(vehicleVO: VehicleVO): VehicleVO{
        val existingVehicle = repository.findByPlaca(vehicleVO.placa)
        if (existingVehicle != null){
            throw ExistingBoardException("This board is already registered!! ${existingVehicle.placa}")
        }
        val vehicle = mapper.toVehicle(vehicleVO)
        val saveVehicle = repository.save(vehicle)
        return mapper.toVehicleVO(saveVehicle)
    }

    fun findAllVehicle(): List<VehicleVO>{
        val vehicle = repository.findAll()
        if (vehicle.isEmpty()){
            throw VehicleNotFoundException("No vehicles found!!")
        }
        return mapper.toVehicleVOList(vehicle)
    }

    fun findByPlaca(placa: String): VehicleVO{
        val vehicle = repository.findByPlaca(placa)
            ?: throw VehicleNotFoundException("Vehicle not found for this $placa")
        return mapper.toVehicleVO(vehicle)
    }

    fun deleteByPlaca(placa: String): VehicleVO{
        val vehicle = repository.findByPlaca(placa)
            ?: throw VehicleNotFoundException("Vehicle not found for this $placa")
        repository.delete(vehicle)
        return mapper.toVehicleVO(vehicle)
    }
}