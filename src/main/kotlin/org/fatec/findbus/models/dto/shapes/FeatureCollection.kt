package org.fatec.findbus.models.dto.shapes

data class FeatureCollection(
    val type: String,
    val features: List<Feature>
)