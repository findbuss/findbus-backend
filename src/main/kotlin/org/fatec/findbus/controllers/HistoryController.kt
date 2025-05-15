package org.fatec.findbus.controllers

import org.fatec.findbus.models.entities.History
import org.fatec.findbus.services.HistoryService
import org.fatec.findbus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/history")
class HistoryController(
    private val historyService: HistoryService,
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    fun getUserHistory(@PathVariable userId: Long): ResponseEntity<List<History>> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        val history = historyService.getUserHistory(user)
        return ResponseEntity.ok(history)
    }

    @PostMapping("/{userId}")
    fun addToHistory(
        @PathVariable userId: Long,
        @RequestParam lineId: String,
        @RequestParam lineName: String,
        @RequestParam shapeId: String
    ): ResponseEntity<History> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        val history = historyService.addToHistory(user, lineId, lineName, shapeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(history)
    }

    @DeleteMapping("/{userId}/{lineId}")
    fun removeFromHistory(
        @PathVariable userId: Long,
        @PathVariable lineId: String
    ): ResponseEntity<Void> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        historyService.removeFromHistory(user, lineId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{userId}")
    fun clearHistory(@PathVariable userId: Long): ResponseEntity<Void> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        historyService.clearHistory(user)
        return ResponseEntity.noContent().build()
    }
}
