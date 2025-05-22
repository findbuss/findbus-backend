package org.fatec.findbus.models.dto.shapes

data class Geometry(
    val type: String,
    val coordinates: List<List<List<Double>>>
)
