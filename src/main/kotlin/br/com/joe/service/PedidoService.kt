package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Pedido
import br.com.joe.entity.dto.PedidoCreateDTO
import br.com.joe.entity.dto.PedidoResponseDTO
import br.com.joe.entity.vo.PedidoVO
import br.com.joe.enums.StatusPedido
import br.com.joe.exception.CpfCnpjInvalidException
import br.com.joe.exception.ProductNotAvailableException
import br.com.joe.exception.ProductNotFoundException
import br.com.joe.exception.UserNotFoundException
import br.com.joe.repository.PedidoRepository
import br.com.joe.repository.ProductRepository
import br.com.joe.repository.UserRepository
import br.com.joe.utils.PedidoUtils
import br.com.joe.utils.validator.CpfCnpjValidator
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PedidoService {

    @Autowired
    private lateinit var pedidoRepository: PedidoRepository

    @Autowired
    private lateinit var userRepository: UserRepository

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
            val validator = CpfCnpjValidator()
            val isValidCpf = validator.isValidCpf(dto.cpf)
            val isValidCnpj = validator.isValidCnpj(dto.cpf)
            if (!isValidCpf && !isValidCnpj){
                throw CpfCnpjInvalidException("CPF or CNPJ not valid")
            }
            val usuario = userRepository.findByCpf(dto.cpf)
                ?: throw UserNotFoundException("Usuário com CPF ${dto.cpf} não encontrado")

            val produtosNaoEncontrados = productRepository.findMissingIds(dto.produtosIDs)
            if (produtosNaoEncontrados.isNotEmpty()){
                throw ProductNotFoundException("Product not found!!")
            }
            val produtos = dto.produtosIDs.mapNotNull { id ->
                productRepository.findById(id).orElse(null)?.takeIf { produto ->
                    produto.status.name == "ATIVO"
                }?.let { produto ->
                    mapper.toProductVO(produto).apply {
                        quantidade = dto.quantidadesPorProduto[id] ?: 1
                    }
                }
            }.toMutableList()

            if (produtos.size != dto.produtosIDs.size){
                throw ProductNotAvailableException("Product not available")
            }

            val userVO = mapper.toUserVO(usuario)
            val numeroPedido = "PED-${pedidoUtils.gerarNumeroPedidoUnico()}"
            val valorTotal = pedidoUtils.calcularValorTotal(produtos)
            val quantidade = produtos.sumOf { it.quantidade }

            val pedidoVO = PedidoVO(
                numeroPedido = numeroPedido,
                user = userVO,
                produtos = produtos,
                status = StatusPedido.RECEBIDO,
                quantidade = quantidade
            )
            val pedido = Pedido(
                numeroPedido = numeroPedido,
                userJson = objectMapper.writeValueAsString(userVO),
                produtosJson = objectMapper.writeValueAsString(produtos),
                status = StatusPedido.RECEBIDO,
                quantidade = quantidade,
                valorTotal = valorTotal
            )
            pedidoRepository.save(pedido)
            mapper.mapPedidoToResponse(pedidoVO, valorTotal)
        }
    }
}