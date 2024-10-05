package com.ead.eshop

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ead.eshop.ui.CartScreen
import com.ead.eshop.ui.CheckoutScreen
import com.ead.eshop.ui.LoginScreen
import com.ead.eshop.ui.Product
import com.ead.eshop.ui.ProductDetailsScreen
import com.ead.eshop.ui.HomeScreen
import com.ead.eshop.ui.RegisterScreen
import com.ead.eshop.ui.WelcomeScreen
import com.ead.eshop.ui.sampleProducts

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartItems = remember { mutableStateListOf<Product>() }

    // Using coroutine scope to retrieve the token
    val scope = rememberCoroutineScope()
    // Use mutableStateOf to hold the token
    var token by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            TokenManager.getToken(navController.context).collect { retrievedToken ->
                token = retrievedToken
                isLoading = false // Set loading to false after retrieving the token
                Log.d("LoginScreen", "Token: $token")
            }
        }
    }

    // Show a loading screen until token is retrieved
    if (isLoading) {
        // Centered Loading Indicator
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center // Center the content
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp), // Set a larger size for the indicator
                strokeWidth = 8.dp // Optionally increase the stroke width
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (token.isNullOrEmpty()) AppRoutes.welcomeScreen else AppRoutes.productListScreen
    ) {
        composable(route = AppRoutes.welcomeScreen) {
            WelcomeScreen(navController)
        }
        composable(route = AppRoutes.loginScreen) {
            LoginScreen(navController)
        }
        composable(route = AppRoutes.registerScreen) {
            RegisterScreen<Any>(navController)
        }
        composable(route = AppRoutes.productListScreen) {
            HomeScreen(
                products = sampleProducts,
                onProductClick = { product ->
                    navController.navigate("product_details/${product.id}") // Navigate to details screen
                },
                navController = navController // Pass NavController for logout navigation
            )
        }


        composable(route = AppRoutes.productDetailsScreen) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = sampleProducts.find { it.id == productId }
            if (product != null) {
                ProductDetailsScreen(product = product) {
                    cartItems.add(product)
                    navController.navigate(AppRoutes.cartScreen)
                }
            }
        }
        composable(route = AppRoutes.cartScreen) {
            CartScreen(
                cartItems = cartItems,
                onCheckout = { navController.navigate(AppRoutes.checkoutScreen) },
                onProductClick = { product ->
                    navController.navigate(AppRoutes.productDetailsScreen + "/${product.id}")
                }
            )
        }
        composable(route = AppRoutes.checkoutScreen) {
            CheckoutScreen {
                cartItems.clear()
                navController.navigate(AppRoutes.productListScreen)
            }
        }
    }
}
