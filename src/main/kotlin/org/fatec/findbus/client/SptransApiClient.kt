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

    fun getBusForecastByStopAndLine(line: String, stop: String): Array<Line> {
        val url = "$apiUrl/position/arrival/$line/$stop"
        return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    }

    fun getStops(): Array<Line> {
        val url = "$apiUrl/stops"
        return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    }
}
