package org.fatec.findbus.controllers

import org.fatec.findbus.config.security.jwt.JwtBuilder
import org.fatec.findbus.models.entities.User
import org.fatec.findbus.services.AuthService
import org.fatec.findbus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/Users")
class UserController(
    private val userService: UserService,
    private val authService: AuthService
) {

    @GetMapping()
    fun buscarUserPorId(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        ): ResponseEntity<User> {
        val userId = authService.validateUserToken(token)
        val user = userService.findById(userId)

        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun adicionarUser(@RequestBody user: User): ResponseEntity<User> {
        val novoUser = userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUser)
    }

    @PutMapping()
    fun atualizarUser(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String,
        @RequestBody user: User
    ): ResponseEntity<User> {
        val id = authService.validateUserToken(token)
        val userExistente = userService.findById(id)
        
        return if (userExistente != null) {
            val userAtualizado = userService.save(
                User(
                    id = id,
                    name = user.name,
                    email = user.email,
                    password = user.password
                )
            )
            ResponseEntity.ok(userAtualizado)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping()
    fun excluirUser(
        @RequestHeader(JwtBuilder.HEADER_AUTHORIZATION) token: String
    ): ResponseEntity<Void> {
        val id = authService.validateUserToken(token)
        val userExistente = userService.findById(id)
        
        return if (userExistente != null) {
            userService.delete(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}