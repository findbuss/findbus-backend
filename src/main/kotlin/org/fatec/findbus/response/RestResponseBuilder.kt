package org.fatec.findbus.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component


@Component
object RestResponseBuilder {
    fun <T> build(
        data: T,
        selfLink: String?,
        success: Boolean,
        status: HttpStatus
    ): ResponseEntity<RestResponse<T>> {
        val response = RestResponse<T>()
        response.setData(data)
        response.setSelfLink(selfLink)
        response.setSuccess(success)
        response.setStatusCode(status.value())
        return ResponseEntity
            .status(response.getStatusCode())
            .body<RestResponse<T>>(response)
    }
}