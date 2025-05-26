package org.fatec.findbus.utils

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {
    @Bean
    fun execute(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}