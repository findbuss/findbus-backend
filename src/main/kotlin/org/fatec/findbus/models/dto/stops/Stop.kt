package org.fatec.findbus.models.dto.stops

import org.fatec.findbus.models.dto.Line

data class Stop(
    val stopId: Long,
    val name: String,
    val lat: Double,
    val lng: Double,
    val lines: List<Line>
)

