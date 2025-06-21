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

    @GetMapping("/lines/search")
    fun searchLineByTerm(@RequestParam term: String): ResponseEntity<List<Line>> {
        val result = sptransService.searchLinesByTerm(term).toList()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/lines/details")
    fun getLineDetailsById(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestParam routeId: String,
        @RequestParam direction: Int
    ): ResponseEntity<org.fatec.findbus.models.dto.LineDetails> {
        try {
            val result = sptransService.getLineDetailsById(token, routeId, direction)
            return ResponseEntity.ok(result)
        } catch (e: org.fatec.findbus.exceptions.JwtValidationException) {
            // Se o token expirou, continuar sem o token
            val result = sptransService.getLineDetailsById(null, routeId, direction)
            return ResponseEntity.ok(result)
        }
    }

    @GetMapping("/shapes")
    fun getLineShape(@RequestParam shapeId: String): ResponseEntity<FeatureCollection> {
        val result = sptransService.getLineShape(shapeId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/position")
    fun getBusPosition(
        @RequestParam(required = false) lineId: String?,
        @RequestParam(required = false) stopId: String?
    ): ResponseEntity<Any> {
        return when {
            lineId != null -> {
                val result = sptransService.getBusPositionByLineId(lineId)
                ResponseEntity.ok(result)
            }
            stopId != null -> {
                val result = sptransService.getLineForecastByStopId(stopId)
                ResponseEntity.ok(result)
            }
            else -> throw IllegalArgumentException("Either lineId or stopId parameter must be provided")
        }
    }

    @GetMapping("/stops")
    fun getStops(
        @RequestParam(required = false) routeId: String?,
        @RequestParam(required = false) stopId: String?,
        @RequestParam(required = false) shapeId: String?,
        @RequestParam(required = false) lon: String?,
        @RequestParam(required = false) lat: String?
    ): ResponseEntity<org.fatec.findbus.models.dto.stops.FeatureCollection> {
        val result = when {
            routeId != null -> sptransService.getStopsByRouteId(routeId)
            stopId != null -> sptransService.getStopsByStopId(stopId)
            shapeId != null -> sptransService.getStopsByShapeId(shapeId)
            lon != null && lat != null -> sptransService.getStopsByLonLat(lon, lat)
            else -> throw IllegalArgumentException("At least one parameter must be provided: routeId, stopId, shapeId, or (lon and lat)")
        }
        return ResponseEntity.ok(result)
    }
}
