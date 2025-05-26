package org.fatec.findbus.config

import org.fatec.findbus.models.entities.User
import org.fatec.findbus.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDatabase {
    private val logger = LoggerFactory.getLogger(LoadDatabase::class.java)

    @Bean
    fun initDatabase(userService: UserService): CommandLineRunner = CommandLineRunner {
        val user = User(
            name = "Admin",
            email = "admin@adm.com",
            password = "admin",
        )
        userService.save(user)
        logger.info("Preloading $user")
    }
}