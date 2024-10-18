package com.ead.eshop.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val stock: Int,
    val isActive: Boolean,
    val lowStockThreshold: Int,
    val vendorId: String,
    val categories: List<Category>,
    val image: String
)