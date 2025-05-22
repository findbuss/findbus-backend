package org.fatec.findbus.models.dto.position

data class Vehicle(
    val prefix: String,
    val accessible: Boolean,
    val hour: String, // ou usar LocalDateTime com deserialização customizada
    val lat: Double,
    val lng: Double
)