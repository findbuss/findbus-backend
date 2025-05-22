package org.fatec.findbus.client

import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SptransApiClient(builder: RestTemplateBuilder) {
    private val restTemplate: RestTemplate = builder.build()
    private val apiUrl = "http://localhost:5000"

    fun searchLinesByTerm(term: String): Array<Line> {
        val url = "$apiUrl/lines/$term"
        return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    }

    fun getLineShape(shapeId: String): FeatureCollection {
        val url = "$apiUrl/shapes/$shapeId"
        return restTemplate.getForObject(url, FeatureCollection::class.java)
            ?: FeatureCollection("FeatureCollection", emptyList())
    }

    fun getBusPositionByLineId(lineId: String): VehiclesResponse {
        val url = "$apiUrl/position/$lineId"
        return restTemplate.getForObject(url, VehiclesResponse::class.java) ?:
            VehiclesResponse("", emptyList())
    }

    fun getLineForecastByStopId(stopId: String): StopResponse {
        val url = "$apiUrl/position/arrival/$stopId"
        return restTemplate.getForObject(url, StopResponse::class.java) ?:
            StopResponse("", org.fatec.findbus.models.dto.stops.Stop(0, "", 0.0, 0.0, emptyList()))
    }

    // fun getBusForecastByStopAndLine(line: String, stop: String): Array<Line> {
    //    val url = "$apiUrl/position/arrival/$line/$stop"
    //    return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    //}

    fun getStopsByRouteId(routeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = "$apiUrl/stops?route_id=$routeId"
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByLonLat(lon: String, lat: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = "$apiUrl/stops?stop_lon=$lon&stop_lat=$lat"
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByShapeId(shapeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = "$apiUrl/stops?shape_id=$shapeId"
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByStopId(stopId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = "$apiUrl/stops?stop_id=$stopId"
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }
}
