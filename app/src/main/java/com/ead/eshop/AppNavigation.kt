package com.ead.eshop

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ead.eshop.ui.HomeScreen
import com.ead.eshop.ui.LoginScreen
import com.ead.eshop.ui.RegisterScreen
import com.ead.eshop.ui.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.welcomeScreen, builder = {
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
            HomeScreen()
        }
    })
}