package com.notes.notes.service

import com.notes.notes.repository.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import com.notes.notes.model.User
import com.notes.notes.util.EmailValidator

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.notes.notes.service.UserDetailsServiceImpl


@Service
class UsersService(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    companion object {
        // Evite le time attack
        const val FAKE_HASH = "\$2a\$10\$7s46EoKwqgSCgL58gT47VOEeeaTfkeWI9eVIdSxM91Ku9lCRmsWmG"
    }

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

    fun login(email: String, password: String): UserDetails {
        
        val user = repository.findByEmail(email)
        val isAuthenticated = passwordEncoder.matches(password, user?.password ?: FAKE_HASH)

        if(!isAuthenticated)
            throw RuntimeException("Une erreur est survenue lors de l'authentification.") 
            
        return user!!.toUserDetails()
    }

}