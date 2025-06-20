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


@Service
class UsersService(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    inner class UserDetailsImpl(private val user: User) : UserDetails {

        override fun getAuthorities(): Collection<GrantedAuthority> {
            // Si User a un champ "roles" ou "role", adapte ici
            return listOf(SimpleGrantedAuthority("ROLE_USER"))
            // Exemple si User a un champ `role: String` :
            // return listOf(SimpleGrantedAuthority(user.role))
        }

        override fun getPassword(): String {
            return user.password
        }

        override fun getUsername(): String {
            return user.email
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }
    }

    fun User.toUserDetails(): UserDetails = UserDetailsImpl(this)

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