package org.fatec.findbus.services

import org.fatec.findbus.models.entities.Usuario
import org.fatec.findbus.models.repositories.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {
    
    fun buscarTodos(): List<Usuario> {
        return usuarioRepository.findAll()
    }
    
    fun buscarPorId(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }
    
    fun buscarPorEmail(email: String): Usuario? {
        return usuarioRepository.findByEmail(email)
    }
    
    @Transactional
    fun salvar(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }
    
    @Transactional
    fun excluir(id: Long) {
        usuarioRepository.deleteById(id)
    }
}