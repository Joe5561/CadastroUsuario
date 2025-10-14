package br.com.joe.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "category")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "categoria", nullable = false)
    var categoria: String = "",

    @JsonIgnore
    @ManyToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    var produtos: MutableList<Product> = mutableListOf()

)