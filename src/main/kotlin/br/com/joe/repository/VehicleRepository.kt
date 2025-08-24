package br.com.joe.repository

import br.com.joe.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository: JpaRepository<Vehicle, Long> {
    fun findByPlaca(placa: String): Vehicle
    fun deleteByPlaca(placa: String): Vehicle
}