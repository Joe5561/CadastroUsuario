package br.com.joe.controllers

import br.com.joe.entity.dto.CategoriaUpdateRequestDTO
import br.com.joe.entity.dto.CategoryResponseDTO
import br.com.joe.entity.vo.CategoryVO
import br.com.joe.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Operations related to categories")
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService

    @PostMapping
    @Operation(summary = "Cadastrar categorias", description = "Efetuar o cadastro de categorias")
    fun saveCategory(@RequestBody categoryVO: CategoryVO): ResponseEntity<CategoryVO> {
        val savedCategoryVO = categoryService.saveCategory(categoryVO)
        savedCategoryVO.add(
            linkTo(
                methodOn(CategoryController::class.java)
                    .saveCategory(savedCategoryVO)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryVO)
    }

    @GetMapping
    @Operation(summary = "Buscar todas as categorias", description = "Efetuar a busca de todas as categorias")
    fun findAllCategory(): ResponseEntity<List<CategoryVO>> {
        val category = categoryService.findAllCategory()
        category.forEach { categoryVO ->
            categoryVO.add(
                linkTo(
                    methodOn(CategoryController::class.java)
                        .findAllCategory()).withSelfRel()
            )
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(category)
    }

    @PatchMapping("novoCategoria")
    @Operation(summary = "Category update")
    fun atualizarCategoria(@RequestBody request: CategoriaUpdateRequestDTO): ResponseEntity<CategoryResponseDTO>{
        val categoryUpdater = categoryService.atualizarCategoria(request.categoria, request.novoCategoria)
        categoryUpdater.add(
            linkTo(
                methodOn(CategoryController::class.java)
                .atualizarCategoria(request)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryUpdater)
    }

    @GetMapping("findByCategory")
    @Operation(summary = "Find category")
    fun findByCategory(@RequestParam categoria: String): ResponseEntity<List<CategoryResponseDTO>>{
        val categoryDTOList = categoryService.findByCategory(categoria.uppercase())
        categoryDTOList.forEach { dto ->
            dto.add(
                linkTo(
                    methodOn(CategoryController::class.java)
                        .findByCategory(dto.categoria)).withSelfRel()
            )
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(categoryDTOList)
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete category")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<EntityModel<Map<String, String>>>{
        categoryService.deleteCategory(id)
        val body = mapOf("message" to "Category with ID $id deleted successfully")
        val selfLink = linkTo(CategoryController::class.java).slash(id).withSelfRel()
        val model = EntityModel.of(body, selfLink)
        return ResponseEntity.ok(model)
    }
}