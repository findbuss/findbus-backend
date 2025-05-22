package org.fatec.findbus.models.dto.stops

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)