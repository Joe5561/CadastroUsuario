package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.UserWithCleanVehiclesVO
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
            throw VehicleAlreadyAssignedException("Vehicle already rented!!")
        }
        vehicle.user = user
        val vehicleUpdated = vehicleRepository.save(vehicle)
        return mapper.toVehicleVOWithUser(vehicleUpdated)
    }

    @Transactional
    fun findUserWithVehicleByCpf(cpf: String): UserWithCleanVehiclesVO{
        val user = userRepository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")

        val vehicles = vehicleRepository.findAllByUserCpf(cpf)
            if (vehicles.isEmpty()){
               throw VehicleNotFoundException("No vehicle linked to user with CPF: $cpf")
            }
        val cleanVehicleVOs = vehicles.map { mapper.toCleanVehicleVO(it) }
        return UserWithCleanVehiclesVO(
            vehicles = cleanVehicleVOs,
            user = mapper.toUserVO(user)
        )
    }

    @Transactional
    fun desvincularVeiculo(placa: String): String{
        val updated = vehicleRepository.desvincularUsuario(placa)
        return if (updated > 0){
            "Veiculo desvinculado com sucesso!!"
        }else
            throw VehicleNotFoundException("Vehicle not found!!")
    }
}