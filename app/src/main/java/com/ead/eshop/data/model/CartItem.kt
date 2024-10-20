package com.ead.eshop.data.model

data class CartItem(
    val id: String?,
    val productId: String,
    val quantity: Int,
    val price: Double,
    val total: Double
)

