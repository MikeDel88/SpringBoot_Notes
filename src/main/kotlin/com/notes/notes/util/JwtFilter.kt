package com.notes.notes.util

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.stereotype.Component

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7) ?: ""

        if(authHeader != null && token != "") {
            if (jwtUtil.validateAccessToken(token)) {
                val userEmail = jwtUtil.getUserFromToken(token)
                val authToken = UsernamePasswordAuthenticationToken(userEmail, null, emptyList())
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
       
        filterChain.doFilter(request, response)
    }
}