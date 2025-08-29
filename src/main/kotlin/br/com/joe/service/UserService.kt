package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.UserVO
import br.com.joe.exception.UserConflictException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    fun save(userVO: UserVO): UserVO {
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

    fun findByName(name: String): UserVO {
        val user = repository.findByName(name)
            ?: throw UserNotFoundException("User not found for this $name")
        return mapper.toUserVO(user)
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

    fun deleteByCpf(cpf: String): UserVO{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
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