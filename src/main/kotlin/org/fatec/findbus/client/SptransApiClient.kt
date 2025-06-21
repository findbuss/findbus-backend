package org.fatec.findbus.client

import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.dto.position.VehiclesResponse
import org.fatec.findbus.models.dto.shapes.FeatureCollection
import org.fatec.findbus.models.dto.stops.StopResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class SptransApiClient(builder: RestTemplateBuilder) {
    private val restTemplate: RestTemplate = builder.build()
    private val apiUrl = "http://localhost:5000"

    fun searchLinesByTerm(term: String): Array<Line> {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/lines/{term}")
            .buildAndExpand(term.trim())
            .toUriString()
        return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    }

    fun getLineShape(shapeId: String): FeatureCollection {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/shapes/{shapeId}")
            .buildAndExpand(shapeId.trim())
            .toUriString()
        return restTemplate.getForObject(url, FeatureCollection::class.java)
            ?: FeatureCollection("FeatureCollection", emptyList())
    }

    fun getLineDetailsById(token: String?, routeId: String, direction: Int): org.fatec.findbus.models.dto.LineDetails {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/lines/details")
            .queryParam("routeId", routeId.trim())
            .queryParam("direction", direction)
            .toUriString()

        val headers = org.springframework.http.HttpHeaders()
        if (token != null) {
            headers.set("Authorization", token)
        }
        val entity = org.springframework.http.HttpEntity<String>(headers)

        return restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, org.fatec.findbus.models.dto.LineDetails::class.java).body
            ?: throw RuntimeException("Failed to get line details")
    }

    fun getBusPositionByLineId(lineId: String): VehiclesResponse {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/position/{lineId}")
            .buildAndExpand(lineId.trim())
            .toUriString()
        return restTemplate.getForObject(url, VehiclesResponse::class.java) ?:
            VehiclesResponse("", emptyList())
    }

    fun getLineForecastByStopId(stopId: String): StopResponse {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/position/arrival/{stopId}")
            .buildAndExpand(stopId.trim())
            .toUriString()
        return restTemplate.getForObject(url, StopResponse::class.java) ?:
            StopResponse("", org.fatec.findbus.models.dto.stops.Stop(0, "", 0.0, 0.0, emptyList()))
    }

    // fun getBusForecastByStopAndLine(line: String, stop: String): Array<Line> {
    //    val url = "$apiUrl/position/arrival/$line/$stop"
    //    return restTemplate.getForObject(url, Array<Line>::class.java) ?: emptyArray()
    //}

    fun getStopsByRouteId(routeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/stops")
            .queryParam("route_id", routeId.trim())
            .toUriString()
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByLonLat(lon: String, lat: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/stops")
            .queryParam("stop_lon", lon.trim())
            .queryParam("stop_lat", lat.trim())
            .toUriString()
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByShapeId(shapeId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/stops")
            .queryParam("shape_id", shapeId.trim())
            .toUriString()
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }

    fun getStopsByStopId(stopId: String): org.fatec.findbus.models.dto.stops.FeatureCollection {
        val url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .path("/stops")
            .queryParam("stop_id", stopId.trim())
            .toUriString()
        return restTemplate.getForObject(url, org.fatec.findbus.models.dto.stops.FeatureCollection::class.java) ?:
            org.fatec.findbus.models.dto.stops.FeatureCollection("FeatureCollection", emptyList())
    }
}
