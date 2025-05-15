package org.fatec.findbus.services

import org.fatec.findbus.models.entities.History
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.HistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class HistoryService(private val historyRepository: HistoryRepository) {

    fun getUserHistory(user: User): List<History> {
        return historyRepository.findByUser(user)
    }

    @Transactional
    fun addToHistory(user: User, lineId: String, lineName: String, shapeId: String): History {
        val existingHistory = historyRepository.findByUserAndLineId(user, lineId)
        
        return if (existingHistory != null) {
            // Update existing history with new date
            val updatedHistory = existingHistory.copy(
                updatedAt = LocalDateTime.now(),
                lineName = lineName,
                shapeId = shapeId
            )
            historyRepository.save(updatedHistory)
        } else {
            // Create new history entry
            val newHistory = History(
                lineId = lineId,
                lineName = lineName,
                shapeId = shapeId,
                user = user
            )
            historyRepository.save(newHistory)
        }
    }

    @Transactional
    fun removeFromHistory(user: User, lineId: String) {
        historyRepository.deleteByUserAndLineId(user, lineId)
    }

    @Transactional
    fun clearHistory(user: User) {
        val userHistory = historyRepository.findByUser(user)
        historyRepository.deleteAll(userHistory)
    }
}
