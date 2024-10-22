package com.ead.eshop.data.model

data class RatingAndComment(
    val customerId: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)
