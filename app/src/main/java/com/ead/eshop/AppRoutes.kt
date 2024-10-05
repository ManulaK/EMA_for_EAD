package com.ead.eshop

object AppRoutes {
    var welcomeScreen = "welcomeScreen"
    var loginScreen = "loginScreen"
    var registerScreen = "registerScreen"

    const val productListScreen = "product_list"
    const val productDetailsScreen = "product_details/{productId}" // Dynamic route for product details
    const val cartScreen = "cart"
    const val checkoutScreen = "checkout"
}