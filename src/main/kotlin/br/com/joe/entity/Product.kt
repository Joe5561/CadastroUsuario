package br.com.joe.entity

import br.com.joe.enums.ProductStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OrderColumn
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "produto")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "nome", nullable = false)
    var nome: String = "",

    @Column(name = "descricao", nullable = false)
    var descricao: String = "",

    @Column(name = "preco", nullable = false)
    var preco: BigDecimal = BigDecimal.ZERO,

    @Column(name = "quantidadeEstoque", nullable = false)
    var quantidadeEstoque: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: ProductStatus = ProductStatus.ATIVO,

    @Column(name = "data_criacao", updatable = false)
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "produto_categoria",
        joinColumns = [JoinColumn(name = "produto_id")],
        inverseJoinColumns = [JoinColumn(name = "categoria_id")]
    )
    @OrderColumn(name = "categoria_ordem")
    var categoria: MutableList<Category> = mutableListOf()


)
