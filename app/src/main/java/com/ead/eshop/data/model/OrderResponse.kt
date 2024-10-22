package com.ead.eshop.data.model

data class OrderResponse(
    val id: String,
    val userId: String,
    val items: List<OrderResponseItem>,
    val orderDate: String,
    val status: String,
    val total: Double,
    val cancellationNote: String?
)


