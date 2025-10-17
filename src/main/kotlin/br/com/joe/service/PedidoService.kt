package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Pedido
import br.com.joe.entity.dto.PedidoCreateDTO
import br.com.joe.entity.dto.PedidoResponseDTO
import br.com.joe.entity.vo.AddressVO
import br.com.joe.entity.vo.PedidoVO
import br.com.joe.entity.vo.ProductVO
import br.com.joe.entity.vo.UserVO
import br.com.joe.enums.StatusPedido
import br.com.joe.repository.PedidoRepository
import br.com.joe.repository.ProductRepository
import br.com.joe.utils.PedidoUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PedidoService {

    @Autowired
    private lateinit var pedidoRepository: PedidoRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var pedidoUtils: PedidoUtils

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Transactional
    fun criarPedidos(dtos: List<PedidoCreateDTO>): List<PedidoResponseDTO> {
        return dtos.map { dto ->
            val numeroPedido = "PED-${pedidoUtils.gerarNumeroPedidoUnico()}"

            val produtos = dto.produtosIDs
                .mapNotNull { id ->
                    productRepository.findById(id).orElse(null)?.let { produto ->
                        val vo = mapper.toProductVO(produto)
                        vo.quantidade = dto.quantidadesPorProduto[id] ?: 1
                        vo
                    }
                }
                .toMutableList()

            val enderecoVOs = dto.user.address.map {
                AddressVO(
                    id = it.id,
                    logradouro = it.logradouro,
                    numero = it.numero,
                    complemento = it.complemento,
                    bairro = it.bairro,
                    cep = it.cep
                )
            }.toMutableList()

            val userVO = UserVO(
                id = dto.user.id,
                name = dto.user.name,
                cpf = dto.user.cpf,
                email = dto.user.email,
                telefone = dto.user.telefone,
                address = enderecoVOs
            )

            val quantidadeTotal = produtos.sumOf { it.quantidade }
            val pedidoVO = PedidoVO(
                numeroPedido = numeroPedido,
                user = userVO,
                produtos = produtos,
                status = StatusPedido.RECEBIDO,
                quantidade = quantidadeTotal
            )


            val quantidade = produtos.sumOf { it.quantidade }
            val valorTotal = pedidoUtils.calcularValorTotal(produtos)

            val userJson = objectMapper.writeValueAsString(userVO)
            val produtosJson = objectMapper.writeValueAsString(produtos)

            val pedidoEntity = Pedido(
                numeroPedido = numeroPedido,
                userJson = userJson,
                produtosJson = produtosJson,
                status = StatusPedido.RECEBIDO,
                quantidade = quantidade,
                valorTotal = valorTotal
            )

            pedidoRepository.save(pedidoEntity)

            PedidoResponseDTO(
                numeroPedido = pedidoVO.numeroPedido,
                user = pedidoVO.user,
                produtos = pedidoVO.produtos.toMutableList(),
                status = StatusPedido.RECEBIDO,
                quantidade = quantidade,
                valorTotal = valorTotal
            )
        }
    }
}