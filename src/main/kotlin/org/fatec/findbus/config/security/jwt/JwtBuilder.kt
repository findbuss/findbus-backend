package org.fatec.findbus.config.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.fatec.findbus.config.security.config.SecurityConfig
import java.util.Date

object JwtBuilder {
    const val HEADER_AUTHORIZATION = "Authorization"
    const val ROLES_AUTHORITIES = "authorities"

    fun build(prefix: String, key: String, jwt: JwtObject): String {
        val token = Jwts.builder()
            .subject(jwt.subject)
            .issuedAt(jwt.issuedAt)
            .expiration(jwt.expiration)
            .claim("email", jwt.email)
            .signWith(Keys.hmacShaKeyFor(SecurityConfig.KEY.toByteArray()))
            .compact()

        return "$prefix $token"
    }

    @Throws(
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        MalformedJwtException::class,
        SignatureException::class
    )
    fun parse(token: String, prefix: String, key: String): JwtObject {
        var cleanedToken = token.removePrefix(prefix).trim()

        val claims = Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(SecurityConfig.KEY.toByteArray()))
            .build()
            .parseClaimsJws(cleanedToken)
            .body

        return JwtObject(
            subject = claims.subject,
            issuedAt = claims.issuedAt,
            expiration = claims.expiration,
            email = claims["email"] as String
        )
    }
}