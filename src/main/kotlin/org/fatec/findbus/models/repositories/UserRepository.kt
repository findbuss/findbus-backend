package org.fatec.findbus.models.repositories

import org.fatec.findbus.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findUserByEmail(email: String): Optional<User>
}