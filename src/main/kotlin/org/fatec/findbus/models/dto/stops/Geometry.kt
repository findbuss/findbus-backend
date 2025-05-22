package org.fatec.findbus.models.dto.stops

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)