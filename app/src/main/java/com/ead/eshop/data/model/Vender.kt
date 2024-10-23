package com.ead.eshop.data.model

data class Vendor(
    val id: String,
    val vendorName: String,
    val vendorDescription: String,
    val averageRating: Double,
    val ratingsAndComments: List<RatingAndComment>
)