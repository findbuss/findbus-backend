package org.fatec.findbus.models.dto.stops

data class Route(
    val route_id: String,
    val agency_id: String,
    val route_short_name: String,
    val route_long_name: String,
    val route_type: Int,
    val route_color: String?,
    val route_text_color: String?
)