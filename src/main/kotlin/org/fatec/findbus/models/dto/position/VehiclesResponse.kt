package org.fatec.findbus.models.dto.position

data class VehiclesResponse(
    val hour: String,
    val vehicles: List<Vehicle>
)