package br.com.joe.entity.vo
import org.springframework.hateoas.RepresentationModel

data class CategoryVO(

    var id: Long = 0,
    var categoria: String = ""

): RepresentationModel<CategoryVO>()