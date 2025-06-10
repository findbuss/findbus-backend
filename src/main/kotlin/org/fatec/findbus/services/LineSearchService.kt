package org.fatec.findbus.services

import org.fatec.findbus.models.dto.Line
import org.springframework.stereotype.Service


@Service
class LineSearchService(
    private val apiClient: org.fatec.findbus.client.SptransApiClient
) {
    fun searchLinesByTerm(term: String): Array<Line> {
        return apiClient.searchLinesByTerm(term)
    }
}
