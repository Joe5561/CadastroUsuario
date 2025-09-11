package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.UserVO
import br.com.joe.exception.CpfCnpjInvalidException
import br.com.joe.exception.IllegalStateException
import br.com.joe.exception.UserConflictException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.repository.UserRepository
import br.com.joe.utils.validator.CpfCnpjValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    fun save(userVO: UserVO): UserVO {
        val validator = CpfCnpjValidator()
        val isValid = validator.isValidCpf(userVO.cpf)
        if (!isValid){
            throw CpfCnpjInvalidException("CPF or CNPJ not valid")
        }

        val existingCpf = repository.findByCpf(userVO.cpf)
        if (existingCpf != null){
            throw UserConflictException("Already registered user!! ${existingCpf.cpf}")
        }
        val user = mapper.toUser(userVO)
        val saveUser = repository.save(user)
        return mapper.toUserVO(saveUser)
    }

    fun findAllUsers(): List<UserVO> {
        val users = repository.findAll()
        if (users.isEmpty()) {
            throw UserNotFoundException("No user found!!!")
        }
        return mapper.toUserVOList(users)
    }

    fun findByName(name: String): List<UserVO> {
        val users = repository.findByName(name)
            ?: throw UserNotFoundException("User not found for this $name")
        return users.map { mapper.toUserVO(it) }
    }

    fun findByEmail(email: String): UserVO {
        val user = repository.findByEmail(email)
            ?: throw UserNotFoundException("User not found for this $email")
        return mapper.toUserVO(user)
    }

    fun findByCpf(cpf: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        return mapper.toUserVO(user)
    }

    fun deleteUser(id: Int){
        val user = repository.findById(id)
        if (user.isPresent){
            repository.deleteById(id)
        }else{
            throw UserNotFoundException("User not found for this $id")
        }
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

    fun atualizarEmail(cpf: String, novoEmail: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        user.email = novoEmail
        val userUpdater = repository.save(user)
        return mapper.toUserVO(userUpdater)
    }
}