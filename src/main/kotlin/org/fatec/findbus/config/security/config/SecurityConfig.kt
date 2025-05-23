package org.fatec.findbus.config.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security.config")
class SecurityConfig {

    fun setPrefix(prefix: String) {
        PREFIX = prefix
    }

    fun setKey(key: String) {
        KEY = key
    }

    fun setExpiration(expiration: Long) {
        EXPIRATION = expiration
    }

    companion object {
        lateinit var PREFIX: String
        lateinit var KEY: String
        var EXPIRATION: Long = 0
    }
}