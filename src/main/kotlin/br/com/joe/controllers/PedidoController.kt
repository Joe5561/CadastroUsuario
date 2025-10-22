package br.com.joe.controllers

import br.com.joe.entity.dto.AlterarStatusPedidoDTO
import br.com.joe.entity.dto.ConsultaPedidoRequestDTO
import br.com.joe.entity.dto.PedidoCreateDTO
import br.com.joe.entity.dto.PedidoResponseDTO
import br.com.joe.exception.CpfCnpjInvalidException
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
import org.springframework.web.bind.annotation.PutMapping

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
            responseDTO.add(
                linkTo(PedidoController::class.java)
                    .slash(responseDTO.numeroPedido)
                    .withSelfRel()
            )
            responseDTO.user.add(
                linkTo(UserController::class.java)
                    .slash(responseDTO.user.id)
                    .withSelfRel()
            )
            responseDTO.user.address.forEach { addressResponse ->
                addressResponse.add(
                    linkTo(AddressController::class.java)
                        .slash(addressResponse.id)
                        .withSelfRel()
                )
            }
            responseDTO.produtos.forEach { productResponse ->
                productResponse.add(
                    linkTo(ProductController::class.java)
                        .slash(productResponse.id)
                        .withSelfRel()
                )
                productResponse.categoria.forEach { categoria ->
                    categoria.add(
                        linkTo(CategoryController::class.java)
                            .slash(categoria.id)
                            .withSelfRel()
                    )
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido)
    }

    @PostMapping("/consultar")
    @Operation(summary = "Consulta de pedidos por CPF, CNPJ ou número do pedido")
    fun consultarPedidos(@RequestBody request: ConsultaPedidoRequestDTO): ResponseEntity<List<PedidoResponseDTO>> {
        val chave = request.cpfOuCnpjOuNumePedido
            ?: throw CpfCnpjInvalidException("Informe CPF/CNPJ ou o número do pedido")
        val pedidos = pedidoService.consultarPedidos(chave)
        pedidos.forEach { responseDTO ->
            responseDTO.add(
                linkTo(PedidoController::class.java)
                    .slash(responseDTO.numeroPedido)
                    .withSelfRel()
            )
            responseDTO.user.add(
                linkTo(UserController::class.java)
                    .slash(responseDTO.user.id)
                    .withSelfRel()
            )
            responseDTO.user.address.forEach { addressResponse ->
                addressResponse.add(
                    linkTo(AddressController::class.java)
                        .slash(addressResponse.id)
                        .withSelfRel()
                )
            }
            responseDTO.produtos.forEach { productResponse ->
                productResponse.add(
                    linkTo(ProductController::class.java)
                        .slash(productResponse.id)
                        .withSelfRel()
                )
                productResponse.categoria.forEach { categoria ->
                    categoria.add(
                        linkTo(CategoryController::class.java)
                            .slash(categoria.id)
                            .withSelfRel()
                    )
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidos)
    }

    @PutMapping("/status")
    @Operation(summary = "Atualizar status do pedido")
    fun alterarStatusPedido(@RequestBody dto: AlterarStatusPedidoDTO): ResponseEntity<PedidoResponseDTO>{
        val pedidoAtualizado = pedidoService.alterarStatusPedido(dto.identificador, dto.novoStatus)
        return ResponseEntity.ok(pedidoAtualizado)
    }
}