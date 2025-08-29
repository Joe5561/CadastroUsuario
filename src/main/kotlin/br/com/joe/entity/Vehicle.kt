package br.com.joe.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "vehicle")
data class Vehicle(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "modelo", nullable = false)
    var modelo: String = "",

    @Column(name = "marca", nullable = false)
    var marca: String = "",

    @Column(name = "ano", nullable = false)
    var ano: String = "",

    @Column(name = "placa", nullable = false, unique = true)
    var placa: String = ""
)
