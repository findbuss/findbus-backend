package org.fatec.findbus.models.repositories

import org.fatec.findbus.models.entities.History
import org.fatec.findbus.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository : JpaRepository<History, Long> {
    fun findByUserId(userId: Long): List<History>
    fun findByUserIdAndLineId(userId: Long, lineId: String): History?
    fun deleteByUserIdAndLineId(userId: Long, lineId: String)
}
