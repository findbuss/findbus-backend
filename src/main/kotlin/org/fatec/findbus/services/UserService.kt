package org.fatec.findbus.services

import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    
    fun findAll(): List<User> {
        return userRepository.findAll()
    }
    
    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
    
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    
    @Transactional
    fun save(user: User): User {
        return userRepository.save(user)
    }
    
    @Transactional
    fun delete(id: Long) {
        userRepository.deleteById(id)
    }
}