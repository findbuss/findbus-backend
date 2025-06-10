package org.fatec.findbus.services

import org.fatec.findbus.models.dto.Line
import org.fatec.findbus.models.entities.History
import org.fatec.findbus.models.repositories.HistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class HistoryService(
    private val historyRepository: HistoryRepository,
    private val userService: UserService,
    private val authService: AuthService,
    private val lineSearchService: LineSearchService
) {

    fun getUserHistory(token: String? = null): List<Line> {
        val userId = if (token != null) authService.validateUserToken(token) else null

        if (userId == null) {
            throw IllegalArgumentException("User ID cannot be null")
        }

        val userHistory = historyRepository.findByUserId(userId)
        var lines: Array<Line> = emptyArray()        
        
        if (userHistory.isNotEmpty()) {
            userHistory.forEach { history ->
                lines += lineSearchService.searchLinesByTerm(history.routeId)
                    .firstOrNull { it.lineId.toString() == history.lineId }
                    ?: throw RuntimeException("Line with ID ${history.lineId} not found")
            }
        }

        return lines.toList()
    }

    @Transactional
    fun addToHistory(userId: Long, lineId: String, routeId: String, lineName: String, shapeId: String): History {
        val existingHistory = historyRepository.findByUserIdAndLineId(userId, lineId)
        val user = userService.findById(userId) ?: throw IllegalArgumentException("User not found")
        
        return if (existingHistory != null) {
            val updatedHistory = existingHistory.copy(
                createdAt = LocalDateTime.now(),
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
