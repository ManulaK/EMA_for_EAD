package com.ead.eshop.data.model

data class RatingAndComment(
    val customerFirstName: String,
    val customerLastName: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)
