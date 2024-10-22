package com.ead.eshop.data.model

data class OrderItems (
    val productId: String,
    val quantity: Int,
    val price: Double,
    val vendorId: String,
    val status: String
)

