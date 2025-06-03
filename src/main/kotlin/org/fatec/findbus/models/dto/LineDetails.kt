package org.fatec.findbus.models.dto

import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection

data class LineDetails(
    val line: Line,
    val stops: org.fatec.findbus.models.dto.stops.FeatureCollection,
    val shapes: FeatureCollection,
    val busPosition: VehiclesResponse
)
