package org.fatec.findbus.services

import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.FavoritesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class FavoritesService(private val favoritesRepository: FavoritesRepository) {

    fun getUserFavorites(user: User): List<Favorites> {
        return favoritesRepository.findByUser(user)
    }

    @Transactional
    fun addToFavorites(user: User, lineId: String, lineName: String, shapeId: String): Favorites {
        val existingFavorite = favoritesRepository.findByUserAndLineId(user, lineId)
        
        return if (existingFavorite != null) {
            // Update existing favorite with new date
            val updatedFavorite = existingFavorite.copy(
                updatedAt = LocalDateTime.now(),
                lineName = lineName,
                shapeId = shapeId
            )
            favoritesRepository.save(updatedFavorite)
        } else {
            // Create new favorite entry
            val newFavorite = Favorites(
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                user = user
            )
            favoritesRepository.save(newFavorite)
        }
    }

    @Transactional
    fun removeFromFavorites(user: User, lineId: String) {
        favoritesRepository.deleteByUserAndLineId(user, lineId)
    }

    fun isFavorite(user: User, lineId: String): Boolean {
        return favoritesRepository.findByUserAndLineId(user, lineId) != null
    }
}
