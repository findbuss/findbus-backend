package org.fatec.findbus.models.repositories

import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoritesRepository : JpaRepository<Favorites, Long> {
    fun findByUserId(userId: Long): List<Favorites>
    fun findByUserIdAndLineId(userId: Long, lineId: String): Favorites?
    fun deleteByUserIdAndLineId(userId: Long, lineId: String)
}
