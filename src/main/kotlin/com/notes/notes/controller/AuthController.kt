package com.notes.notes.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

import com.notes.notes.service.UsersService
import com.notes.notes.util.JwtUtil


@RestController
@RequestMapping("/auth")
class AuthController(
    private val service: UsersService,
    private val jwtUtil: JwtUtil
) {

    data class AuthRequest(
        val email: String,
        val password: String
    )

    data class AuthResponse(
        val accessToken: String,
        val refreshToken: String
    )

    @PostMapping("/register")
    fun register(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
        return try {
            service.register(authRequest.email, authRequest.password)
            ResponseEntity.ok("Utilisateur enregistré.")
        } catch(e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Erreur inconnue lors de l'enregistrement")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
        return try {
            val user = service.login(authRequest.email, authRequest.password)

            val accessToken = jwtUtil.generateToken(user, false)
            val refreshToken = jwtUtil.generateToken(user, true)

            ResponseEntity.ok(AuthResponse(accessToken, refreshToken))

        } catch(e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Erreur inconnue lors de l'enregistrement")
        }
    }
}