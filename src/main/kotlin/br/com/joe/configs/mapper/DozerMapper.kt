package br.com.joe.configs.mapper

import br.com.joe.entity.Address
import br.com.joe.entity.Category
import br.com.joe.entity.Product
import br.com.joe.entity.User
import br.com.joe.entity.Vehicle
import br.com.joe.entity.dto.AddressCreateDTO
import br.com.joe.entity.dto.AddressResponseDTO
import br.com.joe.entity.dto.CategoryDTO
import br.com.joe.entity.dto.VehicleSummaryDTO
import br.com.joe.entity.dto.CleanVehicleDTO
import br.com.joe.entity.dto.ProductCreateDTO
import br.com.joe.entity.dto.ProductResponseDTO
import br.com.joe.entity.dto.UserCreateDTO
import br.com.joe.entity.dto.UserResponseDTO
import br.com.joe.entity.vo.AddressVO
import br.com.joe.entity.vo.CategoryVO
import br.com.joe.entity.vo.ProductVO
import br.com.joe.entity.vo.UserVO
import br.com.joe.entity.vo.VehicleVO
import br.com.joe.enums.ProductStatus
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
        return UserVO(
            id = user.id,
            cpf = user.cpf,
            name = user.name,
            email = user.email,
            telefone = user.telefone,
            address = user.address.map { address ->
                AddressVO(
                    id = address.id,
                    logradouro = address.logradouro,
                    numero = address.numero,
                    complemento = address.complemento,
                    bairro = address.bairro,
                    cep = address.cep
                )
            }
        )
    }

    fun toUserVOList(users: List<User>): List<UserVO> {
        return users.map { user ->
            UserVO(
                id = user.id,
                cpf = user.cpf,
                name = user.name,
                email = user.email,
                telefone = user.telefone,
                address = user.address.map { address ->
                    AddressVO(
                        id = address.id,
                        logradouro = address.logradouro,
                        numero = address.numero,
                        complemento = address.complemento,
                        bairro = address.bairro,
                        cep = address.cep
                    )
                }
            )
        }
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

    fun toUserFromCreateDTO(dto: UserCreateDTO): User {
        val addresses = dto.address.map {
            Address(
                logradouro = it.logradouro,
                numero = it.numero,
                complemento = it.complemento,
                bairro = it.bairro,
                cep = it.cep
            )
        }

        return User(
            name = dto.name,
            cpf = dto.cpf,
            email = dto.email,
            telefone = dto.telefone,
            address = addresses.toMutableList()
        )
    }

    fun toUserResponseDTO(userVO: UserVO): UserResponseDTO {
        val addressDTOs = userVO.address.map {
            AddressResponseDTO(
                id = it.id,
                logradouro = it.logradouro,
                numero = it.numero,
                complemento = it.complemento,
                bairro = it.bairro,
                cep = it.cep
            )
        }

        val dto = UserResponseDTO(
            id = userVO.id,
            name = userVO.name,
            cpf = userVO.cpf,
            email = userVO.email,
            telefone = userVO.telefone,
            address = addressDTOs
        )

        return dto
    }

    fun toAddressFromCreateDTO(dto: AddressCreateDTO): Address {
        return Address(
            logradouro = dto.logradouro,
            numero = dto.numero,
            complemento = dto.complemento,
            bairro = dto.bairro,
            cep = dto.cep
        )
    }

    fun toAddressResponseDTO(address: Address): AddressResponseDTO {
        return AddressResponseDTO(
            id = address.id,
            logradouro = address.logradouro,
            numero = address.numero,
            complemento = address.complemento,
            bairro = address.bairro,
            cep = address.cep
        )
    }

    fun toUserWithAddressVO(user: User): UserVO{
        return UserVO(
            id = user.id,
            name = user.name,
            cpf = user.cpf,
            email = user.email,
            telefone = user.telefone,
            address = user.address.map { address ->
                AddressVO(
                    id = address.id,
                    logradouro = address.logradouro,
                    numero = address.numero,
                    complemento = address.complemento,
                    bairro = address.bairro,
                    cep = address.cep
                )
            }
        )
    }

    fun toUserResponseDTOList(userVOList: List<UserVO>): List<UserResponseDTO> {
        return userVOList.map { userVO ->
            UserResponseDTO(
                id = userVO.id,
                cpf = userVO.cpf,
                name = userVO.name,
                email = userVO.email,
                telefone = userVO.telefone,
                address = userVO.address.map { addressVO ->
                    AddressResponseDTO(
                        id = addressVO.id,
                        logradouro = addressVO.logradouro,
                        numero = addressVO.numero,
                        complemento = addressVO.complemento,
                        bairro = addressVO.bairro,
                        cep = addressVO.cep
                    )
                }
            )
        }
    }

    fun toCategoria(categoryVO: CategoryVO): Category {
        return mapper.map(categoryVO, Category::class.java)
    }

    fun toCategoriaVO(category: Category): CategoryVO {
        return mapper.map(category, CategoryVO::class.java)
    }

    fun toCategoriaVOList(categorias: List<Category>): List<CategoryVO> {
        return categorias.map { mapper.map(it, CategoryVO::class.java) }
    }

    fun toCategoriaList(categoriaVOs: List<CategoryVO>): List<Category> {
        return categoriaVOs.map { mapper.map(it, Category::class.java) }
    }

    fun toProductResponseDTO(productVO: ProductVO): ProductResponseDTO {
        val categoriaDTO = productVO.categoria.map {
            CategoryDTO(id = it.id, nome = it.categoria)
        }

        return ProductResponseDTO(
            id = productVO.id,
            nome = productVO.nome,
            descricao = productVO.descricao,
            preco = productVO.preco.toDouble(),
            quantidadeEstoque = productVO.quantidadeEstoque,
            status = productVO.status,
            categoria = categoriaDTO
        )
    }

    fun toProductVO(product: Product): ProductVO {
        val categoriaVO = product.categoria.map {
            CategoryVO(
                id = it.id,
                categoria = it.categoria
            )
        }

        return ProductVO(
            id = product.id,
            nome = product.nome,
            descricao = product.descricao,
            preco = product.preco.toDouble(),
            quantidadeEstoque = product.quantidadeEstoque,
            status = product.status.name,
            categoria = categoriaVO
        )
    }

    fun toProductFromCreateDTO(dto: ProductCreateDTO, categorias: List<Category>): Product {
        return Product(
            nome = dto.nome,
            descricao = dto.descricao,
            preco = dto.preco.toBigDecimal(),
            quantidadeEstoque = dto.quantidadeEstoque,
            status = ProductStatus.valueOf(dto.status.uppercase()),
            categoria = categorias.toMutableList()
        )
    }

    fun toProductVOList(products: List<Product>): List<ProductVO> {
        return products.map { toProductVO(it) }
    }

}