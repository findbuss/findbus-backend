package org.fatec.findbus.services

import org.fatec.findbus.client.SptransApiClient
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.LineDetails
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.stereotype.Service

@Service
class SptransService(
    private val apiClient: SptransApiClient,
    private val historyService: HistoryService,
    private val authService: AuthService
) {
    fun searchLinesByTerm(term: String): Array<Line> {
        return apiClient.searchLinesByTerm(term)
    }

    fun getLineDetailsById(token: String? = null, routeId: String, direction : Int): LineDetails {
        //TODO Caso de uso do Vendramel
        val userId = if (token != null) authService.validateUserToken(token) else null

        val line = this.searchLinesByTerm(routeId).firstOrNull { it.direction == direction }
            ?: throw RuntimeException("Line with routeId $routeId not found")

        val shapes = this.getLineShape(line.shapeId)
        val stops = this.getStopsByShapeId(line.shapeId)
        val busPositions = this.getBusPositionByLineId(line.lineId.toString())

        if (userId != null){
            historyService.addToHistory(
                userId,
                line.lineId.toString(),
                line.gtfsData.route_id,
                getTerminalByDirection(line, direction),
                line.shapeId
            )
        }
        return LineDetails(line, stops, shapes, busPositions)
    }

    fun getLineShape(shapeId: String): FeatureCollection {
        return apiClient.getLineShape(shapeId)
    }

    fun getBusPositionByLineId(lineId: String): VehiclesResponse {
        return apiClient.getBusPositionByLineId(lineId)
    }

    fun getLineForecastByStopId(stopId: String): StopResponse {
        return apiClient.getLineForecastByStopId(stopId)
    }

    fun getStopsByRouteId(routeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        return apiClient.getStopsByRouteId(routeId)
    }

    fun getStopsByLonLat(lon: String, lat: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        return apiClient.getStopsByLonLat(lon, lat)
    }

    fun getStopsByShapeId(shapeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        return apiClient.getStopsByShapeId(shapeId)
    }

    fun getStopsByStopId(stopId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        return apiClient.getStopsByStopId(stopId)
    }

    private fun getTerminalByDirection(line: Line, direction: Int): String {
        return when (direction) {
            1 -> line.mainTerminal
            2 -> line.secondaryTerminal
            else -> throw IllegalArgumentException("Direção inválida: $direction")
        }
    }
}

