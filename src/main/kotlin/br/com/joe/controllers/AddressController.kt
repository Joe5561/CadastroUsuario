package br.com.joe.controllers

import br.com.joe.entity.vo.AddressVO
import br.com.joe.service.AddressService
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/address")
class AddressController {

    @Autowired
    private lateinit var addressService: AddressService

    @Hidden
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<AddressVO>{
        val addressVO = addressService.findById(id)
        addressVO.add(
            linkTo(methodOn(AddressController::class.java)
                .findById(id)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.OK).body(addressVO)
    }
}