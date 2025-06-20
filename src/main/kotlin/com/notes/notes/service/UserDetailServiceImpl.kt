package com.notes.notes.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import com.notes.notes.repository.UsersRepository
import com.notes.notes.model.User

fun User.toUserDetails(): UserDetails = UserDetailsImpl(this)

class UserDetailsImpl(private val user: User) : UserDetails {

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

@Service
class UserDetailsServiceImpl(
    private val userRepository: UsersRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")
        return user.toUserDetails()
    }
}