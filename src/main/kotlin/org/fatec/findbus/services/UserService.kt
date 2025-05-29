package org.fatec.findbus.services

import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.UserRepository
import org.fatec.findbus.utils.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
    
    @Transactional
    fun save(user: User): User {
        user.password = hashPassword(user.password)
        return userRepository.save(user)
    }
    
    @Transactional
    fun delete(id: Long) {
        userRepository.deleteById(id)
    }

    private fun hashPassword(password: String): String {
        return passwordEncoder.execute().encode(password)
    }
}