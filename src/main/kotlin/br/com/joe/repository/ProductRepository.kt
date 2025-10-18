package br.com.joe.repository

import br.com.joe.entity.Category
import br.com.joe.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    fun existsByNome(nome: String): Boolean
    fun existsByCategoriaContaining(categoria: Category): Boolean?

    @Query("SELECT p.id FROM Product p WHERE p.id IN :ids")
    fun findExistingIds(ids: List<Long>): List<Long>

    fun findMissingIds(ids: List<Long>): List<Long> {
        val existentes = findExistingIds(ids)
        return ids.filterNot { it in existentes }
    }
}