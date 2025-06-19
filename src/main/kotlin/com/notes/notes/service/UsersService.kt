package com.notes.notes.service

import com.notes.notes.repository.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import com.notes.notes.model.User
import com.notes.notes.util.EmailValidator


@Service
class UsersService(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(email: String, password: String): User {
        if(!EmailValidator(email).isValid()) {
            throw IllegalArgumentException("Merci de renseigner un email valide.")
        }

        if (repository.existsByEmail(email)) {
            throw RuntimeException("Une erreur est survenue lors de l'enregistrement.")
        }

        val user = User(
            email = email, 
            password = passwordEncoder.encode(password)
        )

        return repository.save(user);
    }
}