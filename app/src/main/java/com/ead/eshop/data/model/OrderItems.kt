package com.ead.eshop.data.model

data class OrderItems(
    val ProductId: String,
    val Quantity: Int,
    val Price: Double,
    val vendorId: String,
    val ProductName: String? = null,
    val Description: String? = null,
    val imageBase64: String? = null
)

