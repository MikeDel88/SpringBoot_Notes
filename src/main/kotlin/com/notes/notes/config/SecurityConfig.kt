package com.notes.notes.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            }
            .formLogin { it.disable() } // désactive login form si tu ne veux rien
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder();
    }
}