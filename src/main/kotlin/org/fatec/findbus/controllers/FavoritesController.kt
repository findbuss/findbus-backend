package org.fatec.findbus.controllers

import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.services.FavoritesService
import org.fatec.findbus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/favorites")
class FavoritesController(
    private val favoritesService: FavoritesService,
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    fun getUserFavorites(@PathVariable userId: Long): ResponseEntity<List<Favorites>> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        val favorites = favoritesService.getUserFavorites(user)
        return ResponseEntity.ok(favorites)
    }

    @PostMapping("/{userId}")
    fun addToFavorites(
        @PathVariable userId: Long,
        @RequestParam lineId: String,
        @RequestParam lineName: String,
        @RequestParam shapeId: String
    ): ResponseEntity<Favorites> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        val favorite = favoritesService.addToFavorites(user, lineId, lineName, shapeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite)
    }

    @DeleteMapping("/{userId}/{lineId}")
    fun removeFromFavorites(
        @PathVariable userId: Long,
        @PathVariable lineId: String
    ): ResponseEntity<Void> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        favoritesService.removeFromFavorites(user, lineId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{userId}/check")
    fun isFavorite(
        @PathVariable userId: Long,
        @RequestParam lineId: String
    ): ResponseEntity<Map<String, Boolean>> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        val isFavorite = favoritesService.isFavorite(user, lineId)
        return ResponseEntity.ok(mapOf("isFavorite" to isFavorite))
    }
}
