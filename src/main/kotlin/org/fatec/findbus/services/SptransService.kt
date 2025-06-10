package org.fatec.findbus.services

import org.fatec.findbus.client.SptransApiClient
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.LineDetails
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.fatec.findbus.utils.LineMapper
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
        //TODO Caso de uso 2 do Vendramel
        //Verifica se o token de autenticação é válido e extrai o userId (caso o usuário esteja autenticado)
        val userId = if (token != null) authService.validateUserToken(token) else null

        // Busca a linha pelo routeId e destino
        val line = this.searchLinesByTerm(routeId).firstOrNull { it.direction == direction }
            ?: throw RuntimeException("Line with routeId $routeId not found")

        // Busca os detalhes da linha, incluindo trajeto, paradas e posições dos ônibus
        val shapes = this.getLineShape(line.shapeId)
        val stops = this.getStopsByShapeId(line.shapeId)
        val busPositions = this.getBusPositionByLineId(line.lineId.toString())

        //Caso usuário esteja autenticado, adiciona a linha ao histórico dele
        if (userId != null){
            historyService.addToHistory(
                userId,
                line.lineId.toString(),
                line.gtfsData.route_id,
                LineMapper.getTerminalByDirection(line, direction),
                line.shapeId
            )
        }

        // Retorna os detalhes da linha
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
}

