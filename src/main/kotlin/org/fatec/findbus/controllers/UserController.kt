package org.fatec.findbus.controllers

import org.fatec.findbus.models.entities.User
import org.fatec.findbus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/Users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun listarUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.findAll())
    }

    @GetMapping("/{id}")
    fun buscarUserPorId(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
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

    @PutMapping("/{id}")
    fun atualizarUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
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

    @DeleteMapping("/{id}")
    fun excluirUser(@PathVariable id: Long): ResponseEntity<Void> {
        val userExistente = userService.findById(id)
        
        return if (userExistente != null) {
            userService.delete(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}