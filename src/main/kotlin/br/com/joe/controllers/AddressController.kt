package br.com.joe.controllers

import br.com.joe.entity.vo.AddressVO
import br.com.joe.service.AddressService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/address")
@Tag(name = "Address", description = "Operações relacionadas a endereços")
class AddressController {

    @Autowired
    private lateinit var addressService: AddressService

    @PostMapping
    @Operation(summary = "Cadastrar endereços", description = "Efetuar o cadastro de endereços")
    fun saveAddress(@RequestBody addressVO: AddressVO): ResponseEntity<AddressVO>{
        val addressVO = addressService.saveAddress(addressVO)
        addressVO.add(
            linkTo(methodOn(AddressController::class.java)
                .saveAddress(addressVO)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(addressVO)
    }
}