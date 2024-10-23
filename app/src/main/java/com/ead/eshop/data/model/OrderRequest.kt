package com.ead.eshop.data.model

data class OrderRequest (
    val Items: List<OrderItems>,
    val total: Double
)
