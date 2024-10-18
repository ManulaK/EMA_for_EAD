package com.ead.eshop.data.model

data class UpdateRequest(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val address: Address
)
