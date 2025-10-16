package br.com.joe.controllers

import br.com.joe.entity.dto.ProductCreateDTO
import br.com.joe.entity.dto.ProductResponseDTO
import br.com.joe.entity.dto.ProductResponseDTOList
import br.com.joe.entity.vo.ProductVO
import br.com.joe.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Operações relacionadas a produtos")
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @PostMapping
    @Operation(summary = "Cadastrar produtos", description = "Efetuar o cadastro de produtos")
    fun saveProduct(@RequestBody productCreateDTO: ProductCreateDTO): ResponseEntity<ProductResponseDTO>{
        val response = productService.saveProduct(productCreateDTO)
        response.add(linkTo(
            methodOn(ProductController::class.java)
                .saveProduct(productCreateDTO)).withSelfRel())
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Buscar todos produtos")
    fun findAllProductWithCategories(): ResponseEntity<List<ProductResponseDTOList>>{
        val products = productService.findAllProductWithCategories()
        products.forEach { productDTO ->
            productDTO.add(
                linkTo(
                    methodOn(ProductController::class.java)
                        .findAllProductWithCategories()).withSelfRel()
                )
            productDTO.categoria.forEach { categoryResponseDTO ->
                categoryResponseDTO.add(
                    linkTo(
                        methodOn(CategoryController::class.java)
                            .findAllCategory()).withSelfRel()
                    )
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(products)
    }
}