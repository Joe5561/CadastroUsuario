package br.com.joe.controllers

import br.com.joe.entity.vo.CategoryVO
import br.com.joe.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Operations related to categories")
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService

    @PostMapping
    @Operation(summary = "Cadastrar categorias", description = "Efetuar o cadastro de categorias")
    fun saveCategory(@RequestBody categoryVO: CategoryVO): ResponseEntity<CategoryVO>{
        val savedCategoryVO = categoryService.saveCategory(categoryVO)
        savedCategoryVO.add(
            linkTo(methodOn(CategoryController::class.java)
                .saveCategory(savedCategoryVO)).withSelfRel()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryVO)
    }
}