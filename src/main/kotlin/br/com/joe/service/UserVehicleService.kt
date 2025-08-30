package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.UserWithVehiclesVO
import br.com.joe.entity.vo.VehicleVO
import br.com.joe.exception.UserNotFoundException
import br.com.joe.exception.VehicleAlreadyAssignedException
import br.com.joe.exception.VehicleNotFoundException
import br.com.joe.repository.UserRepository
import br.com.joe.repository.VehicleRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserVehicleService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var vehicleRepository: VehicleRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    @Transactional
    fun saveUserAndVehicle(cpf: String, placa: String): VehicleVO{
        val user = userRepository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")

        val vehicle = vehicleRepository.findByPlaca(placa)
            ?: throw VehicleNotFoundException("Vehicle not found for this $placa")

        if (vehicle.user != null && vehicle.user!!.cpf != cpf){
            throw VehicleAlreadyAssignedException("vehicle already rented!!")
        }
        vehicle.user = user
        val vehicleUpdated = vehicleRepository.save(vehicle)
        return mapper.toVehicleVOWithUser(vehicleUpdated)
    }

    @Transactional
    fun findUserWithVehicleByCpf(cpf: String): UserWithVehiclesVO{
        val user = userRepository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")

        val vehicles = vehicleRepository.findFirstByUserCpf(cpf)
            ?: throw VehicleNotFoundException("No vehicle linked to user with CPF: $cpf")

        val vehicleVO = mapper.toVehicleVOWithUser(vehicles)
        return UserWithVehiclesVO(vehicle = vehicleVO)
    }
}