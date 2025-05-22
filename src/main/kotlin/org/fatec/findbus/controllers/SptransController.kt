package org.fatec.findbus.controllers

import org.fatec.findbus.services.SptransService
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/sptrans")
class SptransController(
    private val sptransService: SptransService
) {

    @GetMapping("/lines/{term}")
    fun searchLineByTerm(@PathVariable term: String): ResponseEntity<List<Line>> {
        val result = sptransService.searchLinesByTerm(term).toList()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/shapes/{shapeId}")
    fun getLineShape(@PathVariable shapeId: String): ResponseEntity<FeatureCollection> {
        val result = sptransService.getLineShape(shapeId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/position/{lineId}")
    fun getBusPositionByLineId(@PathVariable lineId: String): ResponseEntity<VehiclesResponse> {
        val result = sptransService.getBusPositionByLineId(lineId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/position/arrival/{stopId}")
    fun getLineForecastByStopId(@PathVariable stopId: String): ResponseEntity<StopResponse> {
        val result = sptransService.getLineForecastByStopId(stopId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/stops/{routeId}")
    fun getStopsByRouteId(@PathVariable routeId: String): ResponseEntity<org.fatec.findbus.models.dto.stops.FeatureCollection> {
        val result = sptransService.getStopsByRouteId(routeId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/stops/{stopId}")
    fun getStopsByStopId(@PathVariable stopId: String): ResponseEntity<org.fatec.findbus.models.dto.stops.FeatureCollection> {
        val result = sptransService.getStopsByStopId(stopId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/stops/{shapeId}")
    fun getStopsByShapeId(@PathVariable shapeId: String): ResponseEntity<org.fatec.findbus.models.dto.stops.FeatureCollection> {
        val result = sptransService.getStopsByShapeId(shapeId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/stops")
    fun getStopsByLonLat(@RequestParam lon: String, @RequestParam lat: String ): ResponseEntity<org.fatec.findbus.models.dto.stops.FeatureCollection> {
        val result = sptransService.getStopsByLonLat(lon, lat)
        return ResponseEntity.ok(result)
    }
}
