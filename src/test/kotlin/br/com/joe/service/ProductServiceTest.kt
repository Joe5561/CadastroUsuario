package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.Category
import br.com.joe.entity.Product
import br.com.joe.entity.dto.CategoryDTO
import br.com.joe.entity.dto.CategoryResponseDTO
import br.com.joe.entity.dto.ProductCreateDTO
import br.com.joe.entity.dto.ProductResponseDTO
import br.com.joe.entity.dto.ProductResponseDTOList
import br.com.joe.entity.dto.ProductStatusUpdateDTO
import br.com.joe.entity.vo.CategoryVO
import br.com.joe.entity.vo.ProductVO
import br.com.joe.enums.ProductStatus
import br.com.joe.exception.CategoryNotFoundException
import br.com.joe.exception.ProductAlreadyExistsException
import br.com.joe.exception.ProductNotFoundException
import br.com.joe.repository.CategoryRepository
import br.com.joe.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var mapper: DozerMapper

    @InjectMocks
    private lateinit var productService: ProductService

    private lateinit var product: Product
    private lateinit var productVO: ProductVO
    private lateinit var productResponseDTO: ProductResponseDTO
    private lateinit var productResponseDTOList: ProductResponseDTOList
    private lateinit var category: Category
    private lateinit var categoryVO: CategoryVO
    private lateinit var categoryDTO: CategoryDTO
    private lateinit var categoryResponseDTO: CategoryResponseDTO

    @BeforeEach
    fun setUp() {
        category = Category(id = 1L, categoria = "Test Category")
        categoryVO = CategoryVO(id = 1L, categoria = "Test Category")
        categoryDTO = CategoryDTO(id = 1L, nome = "Test Category")
        categoryResponseDTO = CategoryResponseDTO(id = 1L, categoria = "Test Category")

        product = Product(
            id = 1L,
            nome = "Test Product",
            descricao = "Description",
            preco = BigDecimal("10.00"),
            quantidadeEstoque = 100,
            status = ProductStatus.ATIVO,
            categoria = mutableListOf(category)
        )
        productVO = ProductVO(
            id = 1L,
            nome = "Test Product",
            descricao = "Description",
            preco = 10.00,
            quantidadeEstoque = 100,
            status = "ATIVO",
            categoria = mutableListOf(categoryVO)
        )
        productResponseDTO = ProductResponseDTO(
            id = 1L,
            nome = "Test Product",
            descricao = "Description",
            preco = 10.00,
            quantidadeEstoque = 100,
            status = "ATIVO",
            categoria = listOf(categoryDTO)
        )
        productResponseDTOList = ProductResponseDTOList(
            id = 1L,
            nome = "Test Product",
            descricao = "Description",
            preco = 10.00,
            quantidadeEstoque = 100,
            status = "ATIVO",
            categoria = listOf(categoryResponseDTO)
        )
    }

    @Test
    fun `findById should return product when found`() {
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))
        `when`(mapper.toProductVO(product)).thenReturn(productVO)
        `when`(mapper.toProductResponseDTO(productVO)).thenReturn(productResponseDTO)

        val result = productService.findById(1L)

        assertNotNull(result)
        assertEquals(product.id, result.id)
    }

    @Test
    fun `findById should throw ProductNotFoundException when not found`() {
        `when`(productRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows(ProductNotFoundException::class.java) {
            productService.findById(1L)
        }
    }

    @Test
    fun `updateStatus should update product status`() {
        val dto = ProductStatusUpdateDTO(id = 1L, status = "INATIVO")
        val savedProduct = product.apply { status = ProductStatus.INATIVO }

        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))
        `when`(productRepository.save(any(Product::class.java))).thenReturn(savedProduct)
        `when`(mapper.toProductVO(savedProduct)).thenReturn(productVO)
        `when`(mapper.toProductResponseDTO(productVO)).thenReturn(productResponseDTO)

        val result = productService.updateStatus(dto)

        assertNotNull(result)
        assertEquals(ProductStatus.INATIVO, product.status)
    }

    @Test
    fun `deleteProduct should delete product`() {
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))
        `when`(productRepository.save(product)).thenReturn(product)
        `when`(mapper.toProductVO(product)).thenReturn(productVO)
        `when`(mapper.toProductResponseDTO(productVO)).thenReturn(productResponseDTO)

        val result = productService.deleteProduct(1L)

        assertNotNull(result)
        assertTrue(product.categoria.isEmpty())
    }

    @Test
    fun `saveProduct should save a new product`() {
        val createDTO = ProductCreateDTO(nome = "New Product", categoria = listOf(1L), descricao = "desc", preco = 20.0, quantidadeEstoque = 50)

        `when`(productRepository.existsByNome("New Product")).thenReturn(false)
        `when`(categoryRepository.findAllById(listOf(1L))).thenReturn(listOf(category))
        `when`(mapper.toProductFromCreateDTO(createDTO, listOf(category))).thenReturn(product)
        `when`(productRepository.save(product)).thenReturn(product)
        `when`(mapper.toProductVO(product)).thenReturn(productVO)
        `when`(mapper.toProductResponseDTO(productVO)).thenReturn(productResponseDTO)

        val result = productService.saveProduct(createDTO)

        assertNotNull(result)
        assertEquals("Test Product", result.nome)
    }

    @Test
    fun `saveProduct should throw ProductAlreadyExistsException if product name exists`() {
        val createDTO = ProductCreateDTO(nome = "Existing Product", categoria = listOf(1L), descricao = "desc", preco = 20.0, quantidadeEstoque = 50)
        `when`(productRepository.existsByNome("Existing Product")).thenReturn(true)

        assertThrows(ProductAlreadyExistsException::class.java) {
            productService.saveProduct(createDTO)
        }
    }

    @Test
    fun `saveProduct should throw CategoryNotFoundException if any category is not found`() {
        val createDTO = ProductCreateDTO(nome = "New Product", categoria = listOf(1L, 2L), descricao = "desc", preco = 20.0, quantidadeEstoque = 50)

        `when`(productRepository.existsByNome("New Product")).thenReturn(false)
        `when`(categoryRepository.findAllById(listOf(1L, 2L))).thenReturn(listOf(category)) // Only returns one category

        assertThrows(CategoryNotFoundException::class.java) {
            productService.saveProduct(createDTO)
        }
    }

    @Test
    fun `findAllProductWithCategories should return a list of products`() {
        val products = listOf(product)
        `when`(productRepository.findAll()).thenReturn(products)
        `when`(mapper.toProductResponseDTOList(anyList())).thenReturn(listOf(productResponseDTOList))

        val result = productService.findAllProductWithCategories()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("Test Product", result[0].nome)
    }
}