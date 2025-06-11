package org.fatec.findbus.config.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.fatec.findbus.config.security.config.SecurityConfig
import org.fatec.findbus.exceptions.AuthenticationFailedException
import java.io.IOException

class JwtFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(JwtBuilder.HEADER_AUTHORIZATION)
        val token = if (header != null && header.startsWith(SecurityConfig.PREFIX)) {
            header.substring(SecurityConfig.PREFIX.length)
        } else null

        try {
            if (!token.isNullOrEmpty()) {
                val tokenObject = JwtBuilder.parse(token, SecurityConfig.PREFIX, SecurityConfig.KEY)

                val userToken = UsernamePasswordAuthenticationToken(
                    tokenObject.subject,
                    null,
                    emptyList()
                )

                SecurityContextHolder.getContext().authentication = userToken
            } else {
                SecurityContextHolder.clearContext()
            }
            filterChain.doFilter(request, response)
        } catch (e: org.fatec.findbus.exceptions.JwtValidationException) {
            // Trate o erro sem lançar uma nova exceção para evitar quebrar o filtro
            response.contentType = "application/json"
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write("{\"timestamp\":\"${java.util.Date()}\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"${e.message}\",\"path\":\"${request.requestURI}\"}")
            return
        } catch (e: Exception) {
            // Trate outras exceções genéricas
            response.contentType = "application/json"
            response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            response.writer.write("{\"timestamp\":\"${java.util.Date()}\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"Erro no processamento da requisição\",\"path\":\"${request.requestURI}\"}")
            return
        }
    }
}