package org.fatec.findbus.services

import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.FavoritesRepository
import org.fatec.findbus.utils.LineMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class FavoritesService(
    private val favoritesRepository: FavoritesRepository,
    private val lineSearchService: LineSearchService,
    private val userService: UserService,
    private val authService: AuthService
) {

    fun getUserFavorites(token: String? = null): List<Line> {
        // Extrai o userID do token de autenticação
        val userId = if (token != null) authService.validateUserToken(token) else null

        // Se não houver ID, gerar uma exceção
        if (userId == null) {
            throw IllegalArgumentException("Identificação do usuário não pode ser nula")
        }

        // Busca os favoritos do usuário
        val favorites =  favoritesRepository.findByUserId(userId)
        var lines: Array<Line> = emptyArray()        // Se houver favoritos, busca informações das linhas correspondentes
        if (favorites.isNotEmpty()) {
            favorites.forEach { favorite ->
                lines += lineSearchService.searchLinesByTerm(favorite.routeId)
                    .firstOrNull{it.lineId.toString() == favorite.lineId}
                    ?: throw org.fatec.findbus.exceptions.LineNotFoundException("Linha com ID ${favorite.lineId} não encontrada")
            }
        }

        return lines.toList()
    }

    @Transactional
    fun addToFavorites(token: String? = null, routeId: String, lineId: String, lineName: String, shapeId: String): Favorites {
        val userId = if (token != null) authService.validateUserToken(token) else null

        if (userId == null) {
            throw IllegalArgumentException("Identificação do usuário não pode ser nula")
        }

        val existingFavorite = favoritesRepository.findByUserIdAndLineId(userId, lineId)
        val user = userService.findById(userId) ?: throw IllegalArgumentException("Usuário não encontrado")

        return if (existingFavorite != null) {
            val updatedFavorite = existingFavorite.copy(
                updatedAt = LocalDateTime.now(),
            )
            favoritesRepository.save(updatedFavorite)
        } else {
            val newFavorite = Favorites(
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                routeId = routeId,
                user = user
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
