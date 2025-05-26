package org.fatec.findbus.services

import org.fatec.findbus.config.security.config.SecurityConfig
import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.config.security.jwt.JwtObject
import org.fatec.findbus.models.dto.auth.LoginDTO
import org.fatec.findbus.models.dto.auth.SessionDTO
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    fun login(login: LoginDTO): SessionDTO {
        val email = login.getEmail() ?: throw IllegalArgumentException("Email cannot be null")
        val user: User = userRepository.findUserByEmail(email)

        if (!BCryptPasswordEncoder().matches(login.getPassword(), user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val jwt = JwtObject().apply {
            issuedAt = Date(System.currentTimeMillis())
            expiration = Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)
            this.email = user.email
        }

        return SessionDTO().apply {
            this.setLogin(user.email)
            this.setToken(JwtBuilder.build(SecurityConfig.PREFIX, SecurityConfig.KEY, jwt))
        }
    }
}
