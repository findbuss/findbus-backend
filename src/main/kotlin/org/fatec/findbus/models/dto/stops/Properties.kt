package org.fatec.findbus.models.dto.stops

data class Properties(
    val stop_id: String,
    val stop_name: String,
    val stop_desc: String?,
    val routes: List<Route>,
    val agency_name: String
)