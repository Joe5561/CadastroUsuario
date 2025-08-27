package br.com.joe.controllers

import br.com.joe.entity.vo.UserVO
import br.com.joe.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
class UserController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping
    @Operation(summary = "Cadastrar usuários", description = "Efetuar o cadastro de usuários")
    fun saveUser(@RequestBody userVO: UserVO): ResponseEntity<UserVO>{
        val userSaveVO = service.save(userVO)
        return ResponseEntity.ok(userSaveVO)
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    fun listUsers(): ResponseEntity<List<UserVO>>{
        val users = service.findAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/email")
    @Operation(summary = "Busca por e-mail", description = "Efetuar a busca por e-mail")
    fun findByEmail(@RequestParam email: String): ResponseEntity<UserVO>{
        val userVO = service.findByEmail(email)
        return ResponseEntity.ok(userVO)
    }

    @GetMapping("/name")
    @Operation(summary = "Busca por nome", description = "Efetua a busca pelo nome")
    fun findByName(@RequestParam name: String): ResponseEntity<UserVO>{
        val userVO = service.findByName(name)
        return ResponseEntity.ok(userVO)
    }

    @PatchMapping("/email")
    @Operation(summary = "Atualizar email")
    fun atualizarEmmail(@RequestParam cpf: String,@RequestParam novoEmail: String): ResponseEntity<UserVO> {
        val userUpdaterVO = service.atualizarEmail(cpf, novoEmail)
        return ResponseEntity.ok(userUpdaterVO)
    }

    @GetMapping("/cpf")
    @Operation(summary = "Buscar por cpf")
    fun findByCpf(@RequestParam cpf: String): ResponseEntity<UserVO>{
        val userVO = service.findByCpf(cpf)
        return ResponseEntity.ok(userVO)
    }

    @DeleteMapping("/cpf")
    @Operation(summary = "Deletar usuário por cpf")
    fun deleteByCpf(@RequestParam cpf: String): ResponseEntity<UserVO>{
        val deleteUserVO = service.deleteByCpf(cpf)
        return ResponseEntity.ok(deleteUserVO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuários")
    fun deleteUser(@PathVariable id: Int){
        service.deleteUser(id)
    }
}