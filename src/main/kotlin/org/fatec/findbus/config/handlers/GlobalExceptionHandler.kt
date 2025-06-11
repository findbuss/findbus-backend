package org.fatec.findbus.config.handlers

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.fatec.findbus.exceptions.*
import org.fatec.findbus.response.RestResponse
import org.fatec.findbus.response.RestResponseBuilder.build
import org.fatec.findbus.utils.GetResponseSelfLink
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun resourceDataNull(ex: HttpRequestMethodNotSupportedException?): ResponseEntity<RestResponse<String>> {
        return build(
            "Method Not Allowed",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.METHOD_NOT_ALLOWED
        )
    }

    @ExceptionHandler(AuthenticationFailedException::class)
    fun authFailed(ex: AuthenticationFailedException): ResponseEntity<RestResponse<String?>> {
        return build(
            ex.message,
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(AuthRequestFailedException::class)
    fun authFailed(ex: AuthRequestFailedException): ResponseEntity<RestResponse<String?>> {
        return build(
            ex.message,
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.UNAUTHORIZED
        )
    }


    @ExceptionHandler(DataIntegrityViolationException::class)
    fun resourceDataNull(ex: DataIntegrityViolationException?): ResponseEntity<RestResponse<String>> {
        return build(
            "Data Integrity Violation",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun resourceDataNull(ex: ConstraintViolationException?): ResponseEntity<RestResponse<String>> {
        return build(
            "Database Constraint Violation",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun resourceDataNull(ex: NoResourceFoundException): ResponseEntity<RestResponse<String?>> {
        return build(
            ex.message,
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(JwtValidationException::class)
    fun handleJwtValidationException(ex: JwtValidationException): ResponseEntity<RestResponse<String?>> {
        log.error("JWT validation error: ${ex.message}", ex)
        return build(
            "Token de autenticação inválido ou expirado: ${ex.message}",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(value = [ExpiredJwtException::class, UnsupportedJwtException::class, 
                              MalformedJwtException::class, SignatureException::class])
    fun handleJwtExceptions(ex: Exception): ResponseEntity<RestResponse<String?>> {
        log.error("JWT error: ${ex.message}", ex)
        return build(
            "Token de autenticação inválido ou expirado",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(LineNotFoundException::class)
    fun handleLineNotFoundException(ex: LineNotFoundException): ResponseEntity<RestResponse<String?>> {
        log.error("Line not found: ${ex.message}", ex)
        return build(
            ex.message,
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<RestResponse<String?>> {
        log.error("Invalid argument: ${ex.message}", ex)
        return build(
            ex.message,
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Throwable::class)
    fun unexpectedException(unexpectedException: Throwable): ResponseEntity<RestResponse<String>> {
        log.error(unexpectedException.message, unexpectedException)
        return build(
            "Unexpected internal server error.",
            GetResponseSelfLink.getSelfLink(),
            false,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}
