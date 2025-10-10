package br.com.joe.controllers

import br.com.joe.entity.dto.UserCreateDTO
import br.com.joe.entity.dto.UserResponseDTO
import br.com.joe.entity.vo.UserVO
import br.com.joe.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
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
    fun saveUser(@RequestBody userDTO: UserCreateDTO): ResponseEntity<UserResponseDTO>{
        val userSaveDTO = service.save(userDTO)
        userSaveDTO.add(
            linkTo(methodOn(UserController::class.java)
                .saveUser(userDTO)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaveDTO)
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    fun listUsers(): ResponseEntity<List<UserResponseDTO>>{
        val users = service.findAllUsers()
        users.forEach { userDTO ->
            userDTO.add(
                linkTo(methodOn(UserController::class.java)
                    .findByCpf(userDTO.cpf)).withSelfRel()
            )
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(users)
    }

    @GetMapping("/email")
    @Operation(summary = "Busca por e-mail", description = "Efetuar a busca por e-mail")
    fun findByEmail(@RequestParam email: String): ResponseEntity<UserResponseDTO>{
        val userDTO = service.findByEmail(email)
        userDTO.add(
            linkTo(methodOn(UserController::class.java)
                .findByEmail(userDTO.email)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.FOUND).body(userDTO)
    }

    @GetMapping("/name")
    @Operation(summary = "Busca por nome", description = "Efetua a busca pelo nome")
    fun findByName(@RequestParam name: String): ResponseEntity<List<UserResponseDTO>>{
        val userDTO = service.findByName(name)
        userDTO.forEach { userDTO ->
            userDTO.add(
                linkTo(methodOn(
                    UserController::class.java)
                    .findByName(userDTO.name)).withSelfRel()
                )
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(userDTO)
    }

    @PatchMapping("/email")
    @Operation(summary = "Atualizar email")
    fun atualizarEmail(@RequestParam cpf: String, @RequestParam novoEmail: String): ResponseEntity<UserResponseDTO> {
        val userUpdaterDTO = service.atualizarEmail(cpf, novoEmail)
        userUpdaterDTO.add(
            linkTo(methodOn(UserController::class.java)
                .atualizarEmail(userUpdaterDTO.cpf, userUpdaterDTO.email)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userUpdaterDTO)
    }

    @PatchMapping("/name")
    @Operation(summary = "Atualizar nome")
    fun atualizarNome(@RequestParam cpf: String, @RequestParam novoNome: String): ResponseEntity<UserResponseDTO>{
        val userUpdateDTO = service.atualizarNome(cpf, novoNome)
        userUpdateDTO.add(
            linkTo(methodOn(UserController::class.java)
                .atualizarNome(userUpdateDTO.cpf, userUpdateDTO.name)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userUpdateDTO)
    }

    @GetMapping("/cpf")
    @Operation(summary = "Buscar por cpf")
    fun findByCpf(@RequestParam cpf: String): ResponseEntity<UserResponseDTO>{
        val userDTO = service.findByCpf(cpf)
        userDTO.add(
            linkTo(methodOn(UserController::class.java)
                .findByCpf(cpf)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.FOUND).body(userDTO)
    }

    @DeleteMapping("/cpf")
    @Operation(summary = "Deletar usuário por cpf")
    fun deleteByCpf(@RequestParam cpf: String): ResponseEntity<UserResponseDTO>{
        val deleteUserDTO = service.deleteByCpf(cpf)
        deleteUserDTO.add(
            linkTo(methodOn(UserController::class.java)
                .deleteByCpf(deleteUserDTO.cpf)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteUserDTO)
    }
}