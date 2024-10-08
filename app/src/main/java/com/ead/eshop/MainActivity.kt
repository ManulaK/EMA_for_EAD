package com.ead.eshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.ead.eshop.data.repository.ProductRepository
import com.ead.eshop.viewmodels.ProductViewModel
import com.ead.eshop.viewmodels.factorys.ProductViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of ProductRepository
        val productRepository = ProductRepository()

        // Create ViewModelFactory and pass repository to it
        val factory = ProductViewModelFactory(productRepository)

        // Create ProductViewModel using ViewModelProvider and factory
        val productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        setContent {
            AppNavigation(productViewModel) // Pass productViewModel to your Composable
        }
    }
}