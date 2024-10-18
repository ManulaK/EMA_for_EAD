package com.ead.eshop

import CheckoutScreen
import OrderScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ead.eshop.ui.CartScreen
import com.ead.eshop.ui.LoginScreen

import com.ead.eshop.ui.ProductDetailsScreen
import com.ead.eshop.ui.HomeScreen
import com.ead.eshop.ui.WelcomeScreen

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ead.eshop.data.model.Product
import com.ead.eshop.ui.ProfileScreen
import com.ead.eshop.ui.RegisterScreen
import com.ead.eshop.ui.SettingsScreen
import com.ead.eshop.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(productViewModel: ProductViewModel) {
    val navController = rememberNavController()
    val cartItems = remember { mutableStateListOf<Pair<Product, Int>>() }

    val scope = rememberCoroutineScope()

    // Token and loading logic (same as before)
    var token by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            TokenManager.getToken(navController.context).collect { retrievedToken ->
                token = retrievedToken
                isLoading = false
                Log.d("LoginScreen", "Token: $token")
            }
        }
    }

    // Show a loading screen until token is retrieved
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.size(24.dp)
                )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (token.isNullOrEmpty()) AppRoutes.welcomeScreen else AppRoutes.homeScreen
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
        composable(route = AppRoutes.homeScreen) {
            HomeScreen(
                navController = navController,
                productViewModel = productViewModel,
                onProductClick = { product ->
                    navController.navigate("product_details/${product.id}")
                }
            )
        }
        composable(route = AppRoutes.productDetailsScreen) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = productViewModel.products.value?.data?.find { it.id == productId }
            if (product != null) {
                ProductDetailsScreen(
                    product = product,
                    navController = navController
                ) { product, quantity ->
                    cartItems.add(product to quantity)
                }
            }
        }

        // Pass cart items to CartScreen
        composable(route = AppRoutes.cartScreen) {
            CartScreen(
                cartItems = cartItems,
                onCheckout = {
                    navController.navigate(AppRoutes.checkoutScreen)
                },
                onProductClick = { product ->
                    navController.navigate(AppRoutes.productDetailsScreen + "/${product.id}")
                },
                onRemoveItem = { product ->
                    // Logic to remove item from cart, or update state

                },
                navController = navController
            )
        }

        composable(route = AppRoutes.checkoutScreen) {
            CheckoutScreen(
                navController = navController,
                cartItems = cartItems,
                onClearCart = {
                    cartItems.clear()
                    navController.navigate(AppRoutes.homeScreen)
                }
            )
        }
        composable(route = AppRoutes.profileScreen) {
            ProfileScreen(navController)
        }
        composable(route = AppRoutes.settingsScreen) {
            SettingsScreen(navController)
        }
        composable(route = AppRoutes.orderScreen) {
            OrderScreen(navController)
        }
    }
}




