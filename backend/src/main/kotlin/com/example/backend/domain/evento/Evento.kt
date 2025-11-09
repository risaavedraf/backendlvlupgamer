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
    val name: String,

    @Lob
    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val date: LocalDateTime,

    @Column(nullable = false)
    val locationName: String,

    @Column(nullable = false)
    val latitude: Double,

    @Column(nullable = false)
    val longitude: Double,

    @OneToMany(mappedBy = "evento", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("evento-imagenes")
    var imagenes: MutableList<EventoImagen> = mutableListOf()
)