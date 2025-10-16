package br.com.joe.entity.dto

import br.com.joe.enums.ProductStatus

data class ProductStatusUpdateDTO(
    var id: Long = 0,
    var status: ProductStatus
)