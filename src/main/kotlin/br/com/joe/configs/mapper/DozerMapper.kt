package br.com.joe.configs.mapper

import br.com.joe.entity.Address
import br.com.joe.entity.User
import br.com.joe.entity.Vehicle
import br.com.joe.entity.dto.VehicleSummaryDTO
import br.com.joe.entity.dto.CleanVehicleDTO
import br.com.joe.entity.vo.AddressVO
import br.com.joe.entity.vo.UserVO
import br.com.joe.entity.vo.VehicleVO
import com.github.dozermapper.core.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class DozerMapper {

    @Autowired
    @Qualifier("dozerCoreMapper")
    lateinit var mapper: Mapper

    fun <O, D> mapObject(origin: O, destination: Class<D>): D{
        return mapper.map(origin, destination)
    }

    fun <O, D> mapList(originList: List<O>, destination: Class<D>): List<D>{
        return originList.map { mapper.map(it, destination) }
    }

    fun toUser(userVO: UserVO): User {
        return mapper.map(userVO, User::class.java)
    }

    fun toUserVO(user: User): UserVO {
        return mapper.map(user, UserVO::class.java)
    }

    fun toUserVOList(users: List<User>): List<UserVO> {
        return users.map { mapper.map(it, UserVO::class.java) }
    }

    fun toUserList(userVOs: List<UserVO>): List<User> {
        return userVOs.map { mapper.map(it, User::class.java) }
    }

    fun toVehicle(vehicleVO: VehicleVO): Vehicle{
        return mapper.map(vehicleVO, Vehicle::class.java)
    }

    fun toVehicleVO(vehicle: Vehicle): VehicleVO{
        return mapper.map(vehicle, VehicleVO::class.java)
    }

    fun toVehicleVOList(vehicles: List<Vehicle>): List<VehicleVO>{
        return vehicles.map { mapper.map(it, VehicleVO::class.java) }
    }

    fun toVehicleList(vehicleVOs: List<VehicleVO>): List<Vehicle>{
        return vehicleVOs.map { mapper.map(it, Vehicle::class.java) }
    }

    fun toVehicleVOWithUser(vehicle: Vehicle): VehicleVO {
        val vehicleVO = mapper.map(vehicle, VehicleVO::class.java)
        vehicle.user?.let {
            vehicleVO.user = mapper.map(it, UserVO::class.java)
        }
        return vehicleVO
    }

    fun toVehicleSummaryDTOList(vehicles: List<Vehicle>): List<VehicleSummaryDTO> {
        return vehicles.map {
            VehicleSummaryDTO(
                id = it.id,
                modelo = it.modelo,
                marca = it.marca,
                ano = it.ano,
                placa = it.placa
            )
        }
    }

    fun toCleanVehicleVO(vehicle: Vehicle): CleanVehicleDTO {
        return mapper.map(vehicle, CleanVehicleDTO::class.java)
    }

    fun toAddress(addressVO: AddressVO): Address {
        return mapper.map(addressVO, Address::class.java)
    }

    fun toAddressVO(address: Address): AddressVO {
        return mapper.map(address, AddressVO::class.java)
    }

    fun toAddressVOList(addresses: List<Address>): List<AddressVO> {
        return addresses.map { mapper.map(it, AddressVO::class.java) }
    }

    fun toAddressList(addressVOs: List<AddressVO>): List<Address> {
        return addressVOs.map { mapper.map(it, Address::class.java) }
    }
}