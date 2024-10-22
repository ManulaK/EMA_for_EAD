package com.ead.eshop.data.model

data class Vendor(
    val id: String,
    val vendorName: String,
    val vendorDescription: String,
    val averageRating: Int,
    val ratingsAndComments: List<RatingAndComment>
)