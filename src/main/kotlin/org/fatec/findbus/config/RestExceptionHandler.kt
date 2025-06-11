package org.fatec.findbus.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.fatec.findbus.exceptions.JwtValidationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class RestExceptionHandler : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: JwtValidationException) {
            // Captura exceções JWT e retorna resposta formatada
            response.contentType = "application/json"
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write("{\"timestamp\":\"${java.util.Date()}\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"${e.message}\",\"path\":\"${request.requestURI}\"}")
        } catch (e: Exception) {
            // Captura exceções genéricas não tratadas
            e.printStackTrace() // Para log
            response.contentType = "application/json"
            response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            response.writer.write("{\"timestamp\":\"${java.util.Date()}\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"Erro no processamento da requisição\",\"path\":\"${request.requestURI}\"}")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // Não filtra requisições estáticas
        return request.requestURI.startsWith("/static/")
    }
}
