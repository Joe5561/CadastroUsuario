package br.com.joe.entity.dto

import org.springframework.hateoas.RepresentationModel

data class CategoryResponseDTO(

    var id: Long = 0,
    var categoria: String = ""

): RepresentationModel<CategoryResponseDTO>()
