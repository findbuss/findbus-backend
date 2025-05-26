package org.fatec.findbus.config.security

import org.fatec.findbus.config.security.jwt.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebSecurityConfig{

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
       http
           .cors{}
           .csrf{ it.disable() }
           .authorizeHttpRequests{ auth ->
               auth
                   .requestMatchers("/api/v1/sptrans/**").permitAll()
                   .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                   .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                   .requestMatchers("/api/v1/**").authenticated()
                   .anyRequest().permitAll()
           }
           .sessionManagement{ sess -> sess
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           }
           .addFilterAfter(JwtFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            }
        }
    }
}