package br.com.joe.entity

import jakarta.persistence.*

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
    var address: String = "",

    @Column(name = "numero", nullable = true)
    var numero: String? = null,

    @Column(name = "telefone", nullable = false)
    var telefone: String = "",

    @Column(name = "cep", nullable = false)
    var cep: String = "",

    @Column(name = "bairro", nullable = false)
    var bairro: String = "",

    @Column(name = "complemento", nullable = false)
    var complemento: String = "",

    @OneToMany(mappedBy = "user", cascade = [], orphanRemoval = false)
    var vehicles: MutableList<Vehicle> = mutableListOf()

)
