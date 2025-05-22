package org.fatec.findbus.models.dto.stops

data class FeatureCollection(
    val type: String,
    val features: List<Feature>?
)