package br.com.joe.entity

import br.com.joe.enums.StatusPedido
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "pedido")
data class Pedido(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var numeroPedido: String = "",

    @Lob
    var userJson: String = "",

    @Lob
    var produtosJson: String = "",

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: StatusPedido = StatusPedido.RECEBIDO,

    @Column(nullable = false)
    var quantidade: Int = 0,

    @Column(nullable = false)
    var valorTotal: Double = 0.0

)