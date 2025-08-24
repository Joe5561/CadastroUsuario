package br.com.joe.repository

import br.com.joe.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
    fun findByName(name: String): User?
    fun findByCpf(cpf: String): User
}