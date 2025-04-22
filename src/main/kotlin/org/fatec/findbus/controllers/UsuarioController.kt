package org.fatec.findbus.controllers

import org.fatec.findbus.models.entities.Usuario
import org.fatec.findbus.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping
    fun listarUsuarios(): ResponseEntity<List<Usuario>> {
        return ResponseEntity.ok(usuarioService.buscarTodos())
    }

    @GetMapping("/{id}")
    fun buscarUsuarioPorId(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.buscarPorId(id)
        return if (usuario != null) {
            ResponseEntity.ok(usuario)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun adicionarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val novoUsuario = usuarioService.salvar(usuario)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario)
    }

    @PutMapping("/{id}")
    fun atualizarUsuario(@PathVariable id: Long, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioExistente = usuarioService.buscarPorId(id)
        
        return if (usuarioExistente != null) {
            val usuarioAtualizado = usuarioService.salvar(
                Usuario(
                    id = id,
                    nome = usuario.nome,
                    email = usuario.email,
                    senha = usuario.senha
                )
            )
            ResponseEntity.ok(usuarioAtualizado)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun excluirUsuario(@PathVariable id: Long): ResponseEntity<Void> {
        val usuarioExistente = usuarioService.buscarPorId(id)
        
        return if (usuarioExistente != null) {
            usuarioService.excluir(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}