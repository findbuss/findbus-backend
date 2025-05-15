package org.fatec.findbus.models.repositories

import org.fatec.findbus.models.entities.Favorites
import org.fatec.findbus.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoritesRepository : JpaRepository<Favorites, Long> {
    fun findByUser(user: User): List<Favorites>
    fun findByUserAndLineId(user: User, lineId: String): Favorites?
    fun deleteByUserAndLineId(user: User, lineId: String)
}
