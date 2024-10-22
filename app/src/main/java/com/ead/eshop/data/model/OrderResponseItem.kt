package com.ead.eshop.data.model

data class OrderResponseItem (
    val productId: String,
    val productName: String,
    val description: String,
    val imageBase64: String,
    val quantity: Int,
    val price: Double,
    val vendorId: String,
    val status: String
)
