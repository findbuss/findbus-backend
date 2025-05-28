package org.fatec.findbus.services

import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.FavoritesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class FavoritesService(
    private val favoritesRepository: FavoritesRepository,
    private val userService: UserService
) {

    fun getUserFavorites(userId: Long): List<Favorites> {
        return favoritesRepository.findByUserId(userId)
    }

    @Transactional
    fun addToFavorites(userId: Long, lineId: String, lineName: String, shapeId: String): Favorites {
        val existingFavorite = favoritesRepository.findByUserIdAndLineId(userId, lineId)
        val user = userService.findById(userId)
        
        return if (existingFavorite != null) {
            val updatedFavorite = existingFavorite.copy(
                updatedAt = LocalDateTime.now(),
                lineName = lineName,
                shapeId = shapeId
            )
            favoritesRepository.save(updatedFavorite)
        } else {
            val newFavorite = Favorites(
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                user = user!!
            )
            favoritesRepository.save(newFavorite)
        }
    }

    @Transactional
    fun removeFromFavorites(userId: Long, lineId: String) {
        favoritesRepository.deleteByUserIdAndLineId(userId, lineId)
    }

    fun isFavorite(userId: Long, lineId: String): Boolean {
        return favoritesRepository.findByUserIdAndLineId(userId, lineId) != null
    }
}
