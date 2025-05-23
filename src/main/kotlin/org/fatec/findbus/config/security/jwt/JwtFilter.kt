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
        val token = request.getHeader(JwtBuilder.HEADER_AUTHORIZATION)
        try {
            if (!token.isNullOrEmpty()) {
                val tokenObject = JwtBuilder.parse(token, SecurityConfig.PREFIX, SecurityConfig.KEY)

                val userToken = UsernamePasswordAuthenticationToken(
                    tokenObject.subject,
                    null,
                )

                SecurityContextHolder.getContext().authentication = userToken
            } else {
                SecurityContextHolder.clearContext()
            }
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            throw AuthenticationFailedException(e.message)
        } catch (e: UnsupportedJwtException) {
            throw AuthenticationFailedException(e.message)
        } catch (e: MalformedJwtException) {
            throw AuthenticationFailedException(e.message)
        } catch (e: SignatureException) {
            throw AuthenticationFailedException(e.message)
        }
    }

    private fun authorities(roles: List<String>): List<SimpleGrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }
}