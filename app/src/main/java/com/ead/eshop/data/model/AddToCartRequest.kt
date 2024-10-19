package com.ead.eshop.data.model

data class AddToCartRequest(
    val productId: String,
    val quantity: Int
)

