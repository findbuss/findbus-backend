package org.fatec.findbus.controllers

import org.fatec.findbus.client.SptransApiClient
import org.fatec.findbus.models.dto.Line
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/sptrans")
class SptransController(
    private val sptransApiClient: SptransApiClient
) {

    @GetMapping("/lines/{term}")
    fun searchLineByTerm(@PathVariable term: String): ResponseEntity<List<Line>> {
        val result = sptransApiClient.searchLinesByTerm(term).toList()
        return ResponseEntity.ok(result)
    }
}
