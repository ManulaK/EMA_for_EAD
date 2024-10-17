package com.ead.eshop.data.model

data class RegisterRequest(
    val username: String,
    val passwordHash: String,
    val role: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val address: Address? = null
)

