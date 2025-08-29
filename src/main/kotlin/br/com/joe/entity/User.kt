package br.com.joe.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "nome", nullable = false)
    var name: String = "",

    @Column(name = "cpf", nullable = false, unique = true)
    var cpf: String = "",

    @Column(name = "email", nullable = false)
    var email: String = "",

    @Column(name = "address", nullable = false)
    var address: String = ""

)
