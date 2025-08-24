package br.com.joe.controllers

import br.com.joe.entity.User
import br.com.joe.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
class UserController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping
    @Operation(summary = "Cadastrar usuários", description = "Efetuar o cadastro de usuários")
    fun saveUser(@RequestBody user: User): ResponseEntity<User>{
        val userSave = service.save(user)
        return ResponseEntity.ok(userSave)
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    fun listUsers(): ResponseEntity<List<User>>{
        val users = service.findAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/email")
    @Operation(summary = "Busca por e-mail", description = "Efetuar a busca por e-mail")
    fun findByEmail(@RequestParam email: String): User?{
        return service.findByEmail(email)
    }

    @GetMapping("/name")
    @Operation(summary = "Busca por nome", description = "Efetua a busca pelo nome")
    fun findByName(@RequestParam name: String): User?{
        return service.findByName(name)
    }

    @PatchMapping("/email")
    @Operation(summary = "Atualizar email")
    fun atualizarEmmail(@RequestParam cpf: String,@RequestParam novoEmail: String): User {
        return service.atualizarEmail(cpf, novoEmail)
    }

    @GetMapping("/cpf")
    @Operation(summary = "Buscar por cpf")
    fun findByCpf(@RequestParam cpf: String): User{
        return service.findByCpf(cpf)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuários")
    fun deleteUser(@PathVariable id: Int){
        service.deleteUser(id)
    }
}