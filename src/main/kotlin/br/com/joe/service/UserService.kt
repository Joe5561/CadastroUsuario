package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.User
import br.com.joe.entity.vo.UserVO
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
        val user = mapper.toUser(userVO)
        val saveUser = repository.save(user)
        return mapper.toUserVO(saveUser)
    }

    fun findAllUsers(): List<User> {
        val users = repository.findAll()
        if (users.isEmpty()) {
            throw UserNotFoundException("No user found!!!")
        }
        return users
    }

    fun findByName(name: String): User? {
        val user = repository.findByName(name)
        if (user != null) {
            return user
        } else {
            throw UserNotFoundException("User not found for this $name")
        }
    }

    fun findByEmail(email: String): User? {
        val user = repository.findByEmail(email)
        if (user != null) {
            return user
        } else {
            throw UserNotFoundException("User not found for this $email")
        }
    }

    fun findByCpf(cpf: String): User{
        val user = repository.findByCpf(cpf)
        if (user != null){
            return user
        }
        throw UserNotFoundException("User not found for this $cpf")
    }

    fun deleteUser(id: Int){
        val user = repository.findById(id)
        if (user.isPresent){
            repository.deleteById(id)
        }else{
            throw UserNotFoundException("User not found for this $id")
        }
    }

    fun deleteByCpf(cpf: String): User{
        val user = repository.findByCpf(cpf)
            ?: throw UserNotFoundException("User not found for this $cpf")
        repository.delete(user)
        return user
    }

    fun atualizarEmail(cpf: String, novoEmail: String): User{
        val user = repository.findByCpf(cpf)
        if (user != null){
            user.email = novoEmail
            return repository.save(user)
        }else{
            throw UserNotFoundException("User not found for this $cpf")
        }
    }
}