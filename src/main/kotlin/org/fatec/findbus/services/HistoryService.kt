package org.fatec.findbus.services

import org.fatec.findbus.models.entities.History
import org.fatec.findbus.models.repositories.HistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class HistoryService(
    private val historyRepository: HistoryRepository,
    private val userService: UserService,
    private val authService: AuthService
) {

    fun getUserHistory(token: String): List<History> {
        val userId = authService.validateUserToken(token)
        return historyRepository.findByUserId(userId)
    }

    @Transactional
    fun addToHistory(userId: Long, lineId: String, routeId: String, lineName: String, shapeId: String): History {
        val existingHistory = historyRepository.findByUserIdAndLineId(userId, lineId)
        val user = userService.findById(userId) ?: throw IllegalArgumentException("User not found")
        
        return if (existingHistory != null) {
            val updatedHistory = existingHistory.copy(
                createdAt = LocalDateTime.now(),
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                routeId = routeId,
            )
            historyRepository.save(updatedHistory)
        } else {
            val newHistory = History(
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                routeId = routeId,
                user = user
            )
            historyRepository.save(newHistory)
        }
    }

    @Transactional
    fun removeFromHistory(userId: Long, lineId: String) {
        historyRepository.deleteByUserIdAndLineId(userId, lineId)
    }

    @Transactional
    fun clearHistory(userId: Long) {
        val userHistory = historyRepository.findByUserId(userId)
        historyRepository.deleteAll(userHistory)
    }
}
