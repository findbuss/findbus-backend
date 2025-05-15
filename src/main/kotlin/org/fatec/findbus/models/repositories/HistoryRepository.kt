package org.fatec.findbus.models.repositories

import org.fatec.findbus.models.entities.History
import org.fatec.findbus.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository : JpaRepository<History, Long> {
    fun findByUser(user: User): List<History>
    fun findByUserAndLineId(user: User, lineId: String): History?
    fun deleteByUserAndLineId(user: User, lineId: String)
}
