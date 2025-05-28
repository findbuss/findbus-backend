package org.fatec.findbus.services

import org.fatec.findbus.config.security.config.SecurityConfig
import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.config.security.jwt.JwtObject
import org.fatec.findbus.exceptions.AuthRequestFailedException
import org.fatec.findbus.exceptions.AuthenticationFailedException
import org.fatec.findbus.models.dto.auth.LoginDTO
import org.fatec.findbus.models.dto.auth.SessionDTO
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.models.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    fun login(login: LoginDTO): SessionDTO {
        try {
            val email = login.getEmail()
            val result: Optional<User> = userRepository.findUserByEmail(email)

            if (result.isEmpty) {
                throw RuntimeException("User not found")
            }

            val user: User = result.get()

            if (!BCryptPasswordEncoder().matches(login.getPassword(), user.password)) {
                throw RuntimeException("Invalid credentials")
            }

            val jwt = JwtObject().apply {
                issuedAt = Date(System.currentTimeMillis())
                expiration = Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)
                subject = user.id.toString()
            }

            return SessionDTO().apply {
                this.setLogin(user.email)
                this.setToken(JwtBuilder.build(SecurityConfig.PREFIX, SecurityConfig.KEY, jwt))
            }
        } catch (e: Exception) {
            throw AuthRequestFailedException("Failed to login, " + e.message)
        }
    }

    fun validateUserToken(header: String): Long {
        val token = if (header.startsWith(SecurityConfig.PREFIX)) {
            header.substring(SecurityConfig.PREFIX.length)
        } else null

        try {
            if (token.isNullOrEmpty()) {
                throw RuntimeException("Token is missing or invalid")
            }

            val jwt = JwtBuilder.parse(token, SecurityConfig.PREFIX, SecurityConfig.KEY)

            if (jwt.expiration!!.before(Date())) {
                throw RuntimeException("Token expired")
            }

            val userId = jwt.subject!!.toLong()
            return userId
        } catch (e: Exception) {
            throw AuthenticationFailedException("Authentication failed, " + e.message)
        }
    }
}
