package org.fatec.findbus.controllers

import org.fatec.findbus.models.dto.auth.LoginDTO
import org.fatec.findbus.models.dto.auth.SessionDTO
import org.fatec.findbus.services.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("auth")
class AuthController(private val authService: AuthService) {

    @PostMapping
    fun login(@RequestBody login: LoginDTO): SessionDTO {
        return authService.login(login)
    }
}