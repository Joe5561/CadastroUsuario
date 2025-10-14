package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Product
import br.com.joe.entity.dto.CategoryDTO
import br.com.joe.entity.dto.ProductCreateDTO
import br.com.joe.entity.dto.ProductResponseDTO
import br.com.joe.entity.vo.ProductVO
import br.com.joe.enums.ProductStatus
import br.com.joe.exception.CategoryNotFoundException
import br.com.joe.exception.ProductAlreadyExistsException
import br.com.joe.repository.CategoryRepository
import br.com.joe.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {

    @Autowired
    private lateinit var mapper: DozerMapper

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Transactional
    fun saveProduct(dto: ProductCreateDTO): ProductResponseDTO {
        if (productRepository.existsByNome(dto.nome)) {
            throw ProductAlreadyExistsException("Produto com nome '${dto.nome}' já está cadastrado.")
        }

        val categoria = categoryRepository.findAllById(dto.categoria)
        if (categoria.size != dto.categoria.size) {
            throw CategoryNotFoundException("Uma ou mais categorias não foram encontradas")
        }
        val product = Product(
            nome = dto.nome,
            descricao = dto.descricao,
            preco = dto.preco.toBigDecimal(),
            quantidadeEstoque = dto.quantidadeEstoque,
            status = ProductStatus.valueOf(dto.status.uppercase()),
            categoria = categoria.distinctBy { it.id }.toMutableList()
        )

        val saved = productRepository.save(product)

        val categoriaDTO = saved.categoria.map {
            CategoryDTO(id = it.id, nome = it.categoria)
        }

        return ProductResponseDTO(
            id = saved.id,
            nome = saved.nome,
            descricao = saved.descricao,
            preco = saved.preco.toDouble(),
            quantidadeEstoque = saved.quantidadeEstoque,
            status = saved.status.name,
            categoria = categoriaDTO
        )
    }
}