package com.ead.eshop.data.model

data class Cart(
    val id: String?,
    val userId: String,
    val items: List<CartItem>,
    val totalAmount: Double
)

