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

    @Column(name = "telefone", nullable = false)
    var telefone: String = "",

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var address: MutableList<Address> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [], orphanRemoval = false)
    var vehicles: MutableList<Vehicle> = mutableListOf()

)
