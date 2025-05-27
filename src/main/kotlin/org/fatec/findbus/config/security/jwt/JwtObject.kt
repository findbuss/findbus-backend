package org.fatec.findbus.config.security.jwt

import java.util.Date

data class JwtObject(
    var subject: String? = null,
    var issuedAt: Date? = null,
    var expiration: Date? = null,
)
