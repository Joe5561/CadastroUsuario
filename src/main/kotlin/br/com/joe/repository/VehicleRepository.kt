package br.com.joe.repository

import br.com.joe.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository: JpaRepository<Vehicle, Long> {
    fun findByPlaca(placa: String): Vehicle?
    fun deleteByPlaca(placa: String): Vehicle?
    fun findFirstByUserCpf(cpf: String): Vehicle?

    @Query("SELECT COUNT(v) > 0 FROM Vehicle v WHERE v.placa = :placa")
    fun existsByPlaca(@Param("placa") placa: String): Boolean

}