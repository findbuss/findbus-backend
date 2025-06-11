package org.fatec.findbus.exceptions

import io.jsonwebtoken.JwtException

class JwtValidationException(message: String?, cause: Throwable? = null) : RuntimeException(message, cause)
