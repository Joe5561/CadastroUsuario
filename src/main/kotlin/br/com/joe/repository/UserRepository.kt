package br.com.joe.repository

import br.com.joe.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
    fun findByNameContainingIgnoreCase(name: String): List<User>?
    fun findByCpf(cpf: String): User?

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address WHERE u.cpf = :cpf")
    fun findByCpfWithAddress(@Param("cpf") cpf: String): User?

    @Query("SELECT u FROM User u JOIN u.vehicles v WHERE v.placa = :placa")
    fun findUsuarioByVeiculoPlaca(@Param("placa") placa: String): User?

}