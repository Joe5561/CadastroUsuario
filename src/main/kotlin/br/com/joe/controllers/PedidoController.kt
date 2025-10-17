package br.com.joe.controllers

import br.com.joe.entity.dto.PedidoCreateDTO
import br.com.joe.entity.dto.PedidoResponseDTO
import br.com.joe.service.PedidoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos")
class PedidoController {

    @Autowired
    private lateinit var pedidoService: PedidoService

    @PostMapping
    @Operation(summary = "Pedidos")
    fun criarPedido(@RequestBody pedidos: List<PedidoCreateDTO>): ResponseEntity<List<PedidoResponseDTO>> {
        val pedido = pedidoService.criarPedidos(pedidos)
        pedido.forEach { responseDTO ->
            responseDTO.user.address.forEach { addressResponse ->
                addressResponse.add(
                    linkTo(methodOn(AddressController::class.java)
                        .findById(addressResponse.id)).withSelfRel()
                )
            }
            responseDTO.user.add(
                linkTo(methodOn(UserController::class.java)
                    .findByCpf(responseDTO.user.cpf)).withSelfRel()
            )
            responseDTO.add(
                linkTo(methodOn(PedidoController::class.java)
                    .criarPedido(pedidos)).withSelfRel()
            )
            responseDTO.produtos.forEach { productResponse ->
                productResponse.add(
                    linkTo(methodOn(ProductController::class.java)
                        .findById(productResponse.id)).withSelfRel()
                )
                productResponse.categoria.forEach { categoria ->
                    categoria.add(
                        linkTo(methodOn(CategoryController::class.java)
                            .findById(categoria.id)).withSelfRel()
                    )
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido)
    }
}