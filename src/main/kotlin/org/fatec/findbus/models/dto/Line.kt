package org.fatec.findbus.models.dto

data class Line(
    val lineId: Int,
    val shapeId: String,
    val circular: Boolean,
    val displaySign: String,
    val direction: Int,
    val type: Int,
    val mainTerminal: String,
    val secondaryTerminal: String,
    val gtfsData: GtfsData
)