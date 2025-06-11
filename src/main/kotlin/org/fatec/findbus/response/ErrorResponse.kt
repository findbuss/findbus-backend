package org.fatec.findbus.response

import java.util.Date

data class ErrorResponse(
    val timestamp: Date = Date(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
