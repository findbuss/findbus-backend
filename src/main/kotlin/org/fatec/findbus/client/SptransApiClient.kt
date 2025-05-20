package org.fatec.findbus.client

import org.fatec.findbus.models.dto.Line
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
}
