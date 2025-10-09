package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.dto.UserCreateDTO
import br.com.joe.entity.dto.UserResponseDTO
import br.com.joe.entity.vo.UserVO
import br.com.joe.exception.CpfCnpjInvalidException
import br.com.joe.exception.EmailInvalidException
import br.com.joe.exception.IllegalStateException
import br.com.joe.exception.PhoneInvalidException
import br.com.joe.exception.UserConflictException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.repository.UserRepository
import br.com.joe.utils.validator.CpfCnpjValidator
import br.com.joe.utils.validator.EmailValidator
import br.com.joe.utils.validator.PhoneValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    @Transactional
    fun save(userDTO: UserCreateDTO): UserResponseDTO {
        val validator = CpfCnpjValidator()
        val isValidCpf = validator.isValidCpf(userDTO.cpf)
        val isValidCnpj = validator.isValidCnpj(userDTO.cpf)
        if (!isValidCpf && !isValidCnpj){
            throw CpfCnpjInvalidException("CPF or CNPJ not valid")
        }
        val existingCpf = repository.findByCpf(userDTO.cpf)
        if (existingCpf != null){
            throw UserConflictException("Already registered user!! ${existingCpf.cpf}")
        }
        val checkEmail = EmailValidator()
        val isValidEmail = checkEmail.isEmailValid(userDTO.email)
        if (!isValidEmail){
            throw EmailInvalidException("E-mail not valid!!")
        }
        val existingEmail = repository.findByEmail(userDTO.email)
        if (existingEmail != null){
            throw UserConflictException("E-mail already registered!! ${existingEmail.email}")
        }
        val checkPhone = PhoneValidator()
        val isValidPhone = checkPhone.isPhoneValid(userDTO.telefone)
        if (!isValidPhone){
            throw PhoneInvalidException("Phone not valid!!")
        }
        val user = mapper.toUserFromCreateDTO(userDTO)
        val saveUser = repository.save(user)
        val userVO = mapper.toUserVO(saveUser)
        return mapper.toUserResponseDTO(userVO)
    }

    @Transactional
    fun findAllUsers(): List<UserResponseDTO> {
        val users = repository.findAll()
        if (users.isEmpty()) {
            throw UserNotFoundException("No user found!!!")
        }
        val userVO = mapper.toUserVOList(users)
        return mapper.toUserResponseDTOList(userVO)
    }

    @Transactional
    fun findByName(name: String): List<UserResponseDTO> {
        val users = repository.findByNameContainingIgnoreCase(name)
            ?: throw UserNotFoundException("User not found!!")
        return users.map { user ->
            val userVO = mapper.toUserVO(user)
            mapper.toUserResponseDTO(userVO)
        }
    }

    @Transactional
    fun findByEmail(email: String): UserResponseDTO {
        val user = repository.findByEmail(email)
            ?: throw UserNotFoundException("User not found for this $email")
        val userVO = mapper.toUserVO(user)
        return mapper.toUserResponseDTO(userVO)
    }

    @Transactional
    fun findByCpf(cpf: String): UserResponseDTO{
        val user = repository.findByCpfWithAddress(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        val userVO = mapper.toUserVO(user)
        return mapper.toUserResponseDTO(userVO)
    }

    @Transactional
    fun deleteByCpf(cpf: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        if (user.vehicles.isNotEmpty()){
            throw IllegalStateException("It is not allowed to delete user with linked vehicles")
        }
        repository.delete(user)
        return mapper.toUserVO(user)
    }

    @Transactional
    fun atualizarEmail(cpf: String, novoEmail: String): UserResponseDTO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        user.email = novoEmail
        val userUpdater = repository.save(user)
        val userVO = mapper.toUserVO(userUpdater)
        return mapper.toUserResponseDTO(userVO)
    }

    @Transactional
    fun atualizarNome(cpf: String, novoNome: String): UserResponseDTO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        user.name = novoNome
        val userUpdate = repository.save(user)
        val userVO = mapper.toUserVO(userUpdate)
        return mapper.toUserResponseDTO(userVO)
    }
}