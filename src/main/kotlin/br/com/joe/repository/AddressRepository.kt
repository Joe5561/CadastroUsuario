package br.com.joe.repository

import br.com.joe.entity.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository: JpaRepository<Address, Long> {
    fun findByNumero(numero: String): Address?
}