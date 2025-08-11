package br.com.joe.controllers

import br.com.joe.entity.User
import br.com.joe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping
    fun saveUser(@RequestBody user: User): ResponseEntity<User>{
        val userSave = service.save(user)
        return ResponseEntity.ok(userSave)
    }

    @GetMapping
    fun listUsers(): ResponseEntity<List<User>>{
        val users = service.findAllUsers()
        return ResponseEntity.ok(users)
    }

}