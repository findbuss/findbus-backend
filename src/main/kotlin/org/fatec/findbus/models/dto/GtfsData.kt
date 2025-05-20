package org.fatec.findbus.models.dto

data class GtfsData(
    val route_id: String,
    val agency_id: String,
    val route_short_name: String,
    val route_long_name: String,
    val route_desc: String? = null,
    val route_type: Int,
    val route_url: String? = null,
    val route_color: String,
    val route_text_color: String,
    val route_sort_order: Int? = null,
    val continuous_pickup: Boolean? = null,
    val continuous_drop_off: Boolean? = null,
    val network_id: String? = null
)