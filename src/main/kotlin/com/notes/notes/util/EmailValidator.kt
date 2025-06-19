package com.notes.notes.util


class EmailValidator(private val email: String) {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

    fun isValid(): Boolean {
        return EMAIL_REGEX.matches(email)
    }
}