package com.ead.eshop.data.model

data class OrderStatusUpdate(
    val status: String,
    val date: String,
    val isCompleted: Boolean
)
