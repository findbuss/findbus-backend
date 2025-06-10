package org.fatec.findbus.controllers

import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.services.AuthService
import org.fatec.findbus.services.HistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/history")
class HistoryController(
    private val historyService: HistoryService,
    private val authService: AuthService
) {
    @GetMapping()
    fun getUserHistory(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String
    ): ResponseEntity<List<Line>>{
        return ResponseEntity.ok(historyService.getUserHistory(token))
    }

    @DeleteMapping("/{lineId}")
    fun removeFromHistory(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        @PathVariable lineId: String
    ): ResponseEntity<Void> {
        val userId = authService.validateUserToken(token)

        historyService.removeFromHistory(userId, lineId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/clear")
    fun clearHistory(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String
    ): ResponseEntity<Void> {
        val userId = authService.validateUserToken(token)

        historyService.clearHistory(userId)
        return ResponseEntity.ok().build()
    }
}