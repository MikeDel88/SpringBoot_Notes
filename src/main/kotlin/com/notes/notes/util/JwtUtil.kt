package com.notes.notes.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import io.jsonwebtoken.security.Keys


@Component
class JwtUtil(@Value("\${jwt.secret}") private val secret: String) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    private val accessTokenValidity = 15 * 60 * 1000; // 15 minutes
    private val refreshTokenValidity = 7 * 24 * 60 * 60 * 1000; // 7 jours

    fun generateToken(user: UserDetails, isRefresh: Boolean): String {
        val now = System.currentTimeMillis()
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        val validity = if(isRefresh) refreshTokenValidity else accessTokenValidity

        return Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + validity))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

}