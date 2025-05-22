package org.fatec.findbus.models.dto.shapes

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)