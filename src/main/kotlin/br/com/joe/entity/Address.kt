package br.com.joe.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne

@Entity
data class Address(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "logradouro", nullable = false)
    var logradouro: String = "",

    @Column(name = "numero", nullable = false)
    var numero: String = "",

    @Column(name = "complemento", nullable = false)
    var complemento: String = "",

    @Column(name = "bairro", nullable = false)
    var bairro: String = "",

    @Column(name = "cep", nullable = false)
    var cep: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: User? = null
)
