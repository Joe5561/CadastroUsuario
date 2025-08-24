package br.com.joe.service

import br.com.joe.entity.User
import br.com.joe.exception.ResourceNotFoundException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    fun save(user: User): User {
        return repository.save(user)
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

    fun deleteUser(id: Int){
        val user = repository.findById(id)
        if (user.isPresent){
            repository.deleteById(id)
        }else{
            throw UserNotFoundException("User not found for this $id")
        }
    }
}