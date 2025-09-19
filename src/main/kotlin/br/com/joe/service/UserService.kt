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

    @Transactional
    fun save(userVO: UserVO): UserVO {
        val validator = CpfCnpjValidator()
        val isValidCpf = validator.isValidCpf(userVO.cpf)
        val isValidCnpj = validator.isValidCnpj(userVO.cpf)
        if (!isValidCpf && !isValidCnpj){
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

    @Transactional
    fun findAllUsers(): List<UserVO> {
        val users = repository.findAll()
        if (users.isEmpty()) {
            throw UserNotFoundException("No user found!!!")
        }
        return mapper.toUserVOList(users)
    }

    @Transactional
    fun findByName(name: String): List<UserVO> {
        val users = repository.findByNameContainingIgnoreCase(name)
            ?: throw UserNotFoundException("User not found for this $name")
        return users.map { mapper.toUserVO(it) }
    }

    @Transactional
    fun findByEmail(email: String): UserVO {
        val user = repository.findByEmail(email)
            ?: throw UserNotFoundException("User not found for this $email")
        return mapper.toUserVO(user)
    }

    @Transactional
    fun findByCpf(cpf: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        return mapper.toUserVO(user)
    }

    @Transactional
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

    @Transactional
    fun atualizarEmail(cpf: String, novoEmail: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        user.email = novoEmail
        val userUpdater = repository.save(user)
        return mapper.toUserVO(userUpdater)
    }

    @Transactional
    fun atualizarNome(cpf: String, novoNome: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        user.name = novoNome
        val userUpdate = repository.save(user)
        return mapper.toUserVO(userUpdate)
    }
}