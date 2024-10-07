package com.ead.eshop.data.model

data class Order(
    val id: String,
    val date: String,
    val total: Double,
    val status: String,
    val progress: Float,
    val statusUpdates: List<OrderStatusUpdate>
)

data class OrderStatusUpdate(
    val status: String,
    val date: String,
    val isCompleted: Boolean
) {

}

// Sample orders
val sampleOrders = listOf(
    Order(
        id = "12345",
        date = "2024-10-01",
        total = 150.0,
        status = "Delivered",
        progress = 1.0f,
        statusUpdates = listOf(
            OrderStatusUpdate("Order Placed", "2024-09-27", true),
            OrderStatusUpdate("Shipped", "2024-09-29", true),
            OrderStatusUpdate("Out for Delivery", "2024-10-01", true),
            OrderStatusUpdate("Delivered", "2024-10-01", true)
        )
    ),
    Order(
        id = "67890",
        date = "2024-09-25",
        total = 99.99,
        status = "Shipped",
        progress = 0.7f,
        statusUpdates = listOf(
            OrderStatusUpdate("Order Placed", "2024-09-20", true),
            OrderStatusUpdate("Shipped", "2024-09-23", true),
            OrderStatusUpdate("Out for Delivery", "2024-09-25", false)
        )
    )
)
