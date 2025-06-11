package org.fatec.findbus.config.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.fatec.findbus.config.security.config.SecurityConfig
import java.util.Date

object JwtBuilder {
    const val HEADER_AUTHORIZATION = "Authorization"

    fun build(prefix: String, key: String, jwt: JwtObject): String {
        val token = Jwts.builder()
            .subject(jwt.subject)
            .issuedAt(jwt.issuedAt)
            .expiration(jwt.expiration)
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
        try {
            var cleanedToken = token.removePrefix(prefix).trim()

            val claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(key.toByteArray()))
                .build()
                .parseClaimsJws(cleanedToken)
                .body

            return JwtObject(
                subject = claims.subject,
                issuedAt = claims.issuedAt,
                expiration = claims.expiration,
            )
        } catch (ex: ExpiredJwtException) {
            throw org.fatec.findbus.exceptions.JwtValidationException("Token expirado", ex)
        } catch (ex: UnsupportedJwtException) {
            throw org.fatec.findbus.exceptions.JwtValidationException("Token não suportado", ex)
        } catch (ex: MalformedJwtException) {
            throw org.fatec.findbus.exceptions.JwtValidationException("Token mal formatado", ex)
        } catch (ex: SignatureException) {
            throw org.fatec.findbus.exceptions.JwtValidationException("Assinatura de token inválida", ex)
        } catch (ex: Exception) {
            throw org.fatec.findbus.exceptions.JwtValidationException("Erro ao processar token", ex)
        }
    }
}