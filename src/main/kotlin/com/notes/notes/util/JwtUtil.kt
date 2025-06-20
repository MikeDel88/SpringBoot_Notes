package com.notes.notes.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Claims
import java.util.Date
import javax.crypto.SecretKey
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatusCode


@Component
class JwtUtil(@Value("\${jwt.secret}") private val secret: String) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    private val accessTokenValidity = 15 * 60 * 1000; // 15 minutes
    private val refreshTokenValidity = 7 * 24 * 60 * 60 * 1000; // 7 jours

    fun generateToken(user: UserDetails, isRefresh: Boolean): String {
        val validity = if(isRefresh) refreshTokenValidity else accessTokenValidity
        val now = Date()
        val expiryDate = Date(now.time + validity)
        val type = if(isRefresh) "refresh" else "access"

        return Jwts.builder()
            .setSubject(user.username)
            .claim("type", type)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun getUserFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(401),
            "Invalid token."
        )
        return claims.subject
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if(token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        } else token
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(rawToken)
                .body
        } catch(e: Exception) {
            null
        }
    }

}