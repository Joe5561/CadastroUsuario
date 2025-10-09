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
    fun listUsers(): ResponseEntity<List<UserVO>>{
        val users = service.findAllUsers()
        users.forEach { userVO ->
            userVO.add(
                linkTo(methodOn(UserController::class.java)
                    .findByCpf(userVO.cpf)).withSelfRel()
            )
        }
        return ResponseEntity.status(HttpStatus.OK).body(users)
    }

    @GetMapping("/email")
    @Operation(summary = "Busca por e-mail", description = "Efetuar a busca por e-mail")
    fun findByEmail(@RequestParam email: String): ResponseEntity<UserVO>{
        val userVO = service.findByEmail(email)
        userVO.add(
            linkTo(methodOn(UserController::class.java)
                .findByEmail(userVO.email)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userVO)
    }

    @GetMapping("/name")
    @Operation(summary = "Busca por nome", description = "Efetua a busca pelo nome")
    fun findByName(@RequestParam name: String): ResponseEntity<List<UserVO>>{
        val usersVO = service.findByName(name)
        usersVO.forEach { userVO ->
            userVO.add(
                linkTo(methodOn(
                    UserController::class.java)
                    .findByName(userVO.name)).withSelfRel()
                )
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersVO)
    }

    @PatchMapping("/email")
    @Operation(summary = "Atualizar email")
    fun atualizarEmmail(@RequestParam cpf: String, @RequestParam novoEmail: String): ResponseEntity<UserVO> {
        val userUpdaterVO = service.atualizarEmail(cpf, novoEmail)
        userUpdaterVO.add(
            linkTo(methodOn(UserController::class.java)
                .atualizarEmmail(userUpdaterVO.cpf, userUpdaterVO.email)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userUpdaterVO)
    }

    @PatchMapping("/name")
    @Operation(summary = "Atualizar nome")
    fun atualizarNome(@RequestParam cpf: String, @RequestParam novoNome: String): ResponseEntity<UserVO>{
        val userUpdateVO = service.atualizarNome(cpf, novoNome)
        userUpdateVO.add(
            linkTo(methodOn(UserController::class.java)
                .atualizarNome(userUpdateVO.cpf, userUpdateVO.name)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userUpdateVO)
    }

    @GetMapping("/cpf")
    @Operation(summary = "Buscar por cpf")
    fun findByCpf(@RequestParam cpf: String): ResponseEntity<UserResponseDTO>{
        val userDTO = service.findByCpf(cpf)
        userDTO.add(
            linkTo(methodOn(UserController::class.java)
                .findByCpf(cpf)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(userDTO)
    }

    @DeleteMapping("/cpf")
    @Operation(summary = "Deletar usuário por cpf")
    fun deleteByCpf(@RequestParam cpf: String): ResponseEntity<UserVO>{
        val deleteUserVO = service.deleteByCpf(cpf)
        deleteUserVO.add(
            linkTo(methodOn(UserController::class.java)
                .deleteByCpf(deleteUserVO.cpf)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteUserVO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuários")
    fun deleteUser(@PathVariable id: Int){
        service.deleteUser(id)
    }
}