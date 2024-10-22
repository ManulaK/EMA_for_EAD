package com.ead.eshop.data.model

data class Review(
    val name: String,
    val rating: Double,
    val date: String,
    val comment: String,
    val imageUrl: String = ""
)
