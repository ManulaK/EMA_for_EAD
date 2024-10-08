package com.ead.eshop.data.model

data class UserDetailsResponse(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val address: Address
)
