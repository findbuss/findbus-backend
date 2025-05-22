package org.fatec.findbus.services

import org.fatec.findbus.client.SptransApiClient
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.stereotype.Service

@Service
class SptransService(
    private val apiClient: SptransApiClient
) {
    fun searchLinesByTerm(term: String): Array<Line> {
        return apiClient.searchLinesByTerm(term)
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