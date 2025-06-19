package com.notes.notes.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

import com.notes.notes.service.UsersService


@RestController
@RequestMapping("/auth")
class AuthController(private val service: UsersService) {

    data class RegisterRequest(
        val email: String,
        val password: String
    )

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
        return try {
            service.register(registerRequest.email, registerRequest.password)
            ResponseEntity.ok("Utilisateur enregistré.")
        } catch(e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Erreur inconnue lors de l'enregistrement")
        }
    }

}