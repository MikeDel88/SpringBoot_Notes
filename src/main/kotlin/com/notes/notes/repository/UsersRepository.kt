package com.notes.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.notes.notes.model.User

@Repository
interface UsersRepository : JpaRepository<User, Long> {
    
    fun existsByEmail(email: String): Boolean
}
