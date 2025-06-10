package org.fatec.findbus.controllers

import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.entities.History
import org.fatec.findbus.services.HistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/history")
class HistoryController(
    private val historyService: HistoryService,
) {
    @GetMapping()
    fun getUserHistory(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String
    ): ResponseEntity<List<Line>>{
        return ResponseEntity.ok(historyService.getUserHistory(token))
    }
}