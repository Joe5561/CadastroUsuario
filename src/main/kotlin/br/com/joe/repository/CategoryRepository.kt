package br.com.joe.repository

import br.com.joe.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByCategoria(categoria: String): Category?
    fun findByCategoriaContainingIgnoreCase(categoria: String): List<Category>?
}