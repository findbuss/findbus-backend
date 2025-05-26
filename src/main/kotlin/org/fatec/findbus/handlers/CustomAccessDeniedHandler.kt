package org.fatec.findbus.handlers


import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.fatec.findbus.response.RestResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN // 403
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val responseBody: RestResponse<String> = RestResponse<String>()
        responseBody.setSuccess(false)
        responseBody.setData("Access Denied")
        responseBody.setSelfLink(request.requestURI)
        responseBody.setStatusCode(HttpServletResponse.SC_FORBIDDEN)

        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, responseBody)
    }
}

