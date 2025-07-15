package br.com.joe.service

import br.com.joe.entity.User
import br.com.joe.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    fun save(user: User): User{
        return repository.save(user)
    }

}