package org.fatec.findbus.controllers

import org.fatec.findbus.client.SptransApiClient
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/sptrans")
class SptransController(
    private val sptransApiClient: SptransApiClient
) {

    @GetMapping("/lines/{term}")
    fun searchLineByTerm(@PathVariable term: String): ResponseEntity<List<Line>> {
        val result = sptransApiClient.searchLinesByTerm(term).toList()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/shapes/{shapeId}")
    fun getLineShape(@PathVariable shapeId: String): ResponseEntity<FeatureCollection> {
        val result = sptransApiClient.getLineShape(shapeId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/position/{lineId}")
    fun getBusPositionByLineId(@PathVariable lineId: String): ResponseEntity<VehiclesResponse> {
        val result = sptransApiClient.getBusPositionByLineId(lineId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/position/arrival/{stopId}")
    fun getLineForecastByStopId(@PathVariable stopId: String): ResponseEntity<StopResponse> {
        val result = sptransApiClient.getLineForecastByStopId(stopId)
        return ResponseEntity.ok(result)
    }
}
