package org.fatec.findbus.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "history")
data class History(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "line_id", nullable = false)
    val lineId: String,

    @Column(name = "route_id", nullable = false)
    val routeId: String,

    @Column(name = "line_name", nullable = false)
    val lineName: String,

    @Column(name = "shape_id", nullable = false)
    val shapeId: String,

    @Column(name = "updated_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    val user: User
)
