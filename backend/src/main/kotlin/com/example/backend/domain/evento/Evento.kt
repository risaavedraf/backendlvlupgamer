package com.example.backend.domain.evento

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "eventos")
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String, // Cambiado de val a var

    @Lob
    @Column(nullable = false)
    var description: String, // Cambiado de val a var

    @Column(nullable = false)
    var date: LocalDateTime, // Cambiado de val a var

    @Column(nullable = false)
    var locationName: String, // Cambiado de val a var

    @Column(nullable = false)
    var latitude: Double, // Cambiado de val a var

    @Column(nullable = false)
    var longitude: Double, // Cambiado de val a var

    @OneToMany(mappedBy = "evento", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("evento-imagenes")
    var imagenes: MutableList<EventoImagen> = mutableListOf()
)
