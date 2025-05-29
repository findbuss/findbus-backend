package org.fatec.findbus.controllers

import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.services.AuthService
import org.fatec.findbus.services.FavoritesService
import org.fatec.findbus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/favorites")
class FavoritesController(
    private val favoritesService: FavoritesService,
    private val authService: AuthService
) {

    @GetMapping()
    fun getUserFavorites(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
    ): ResponseEntity<List<Favorites>> {
        val userId = authService.validateUserToken(token)

        val favorites = favoritesService.getUserFavorites(userId)
        return ResponseEntity.ok(favorites)
    }

    @PostMapping()
    fun addToFavorites(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        @RequestParam lineId: String,
        @RequestParam lineName: String,
        @RequestParam shapeId: String
    ): ResponseEntity<Favorites> {
        val userId = authService.validateUserToken(token)

        val favorite = favoritesService.addToFavorites(userId, lineId, lineName, shapeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite)
    }

    @DeleteMapping("/{lineId}")
    fun removeFromFavorites(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        @PathVariable lineId: String
    ): ResponseEntity<Void> {
        val userId = authService.validateUserToken(token)

        favoritesService.removeFromFavorites(userId, lineId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/check")
    fun isFavorite(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        @RequestParam lineId: String
    ): ResponseEntity<Map<String, Boolean>> {
        val userId = authService.validateUserToken(token)

        val isFavorite = favoritesService.isFavorite(userId, lineId)
        return ResponseEntity.ok(mapOf("isFavorite" to isFavorite))
    }
}
