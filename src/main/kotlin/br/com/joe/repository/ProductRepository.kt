package br.com.joe.repository

import br.com.joe.entity.Category
import br.com.joe.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    fun existsByNome(nome: String): Boolean
    fun existsByCategoriaContaining(categoria: Category): Boolean?
}