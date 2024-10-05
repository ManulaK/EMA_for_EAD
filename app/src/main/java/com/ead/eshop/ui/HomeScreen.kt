package com.ead.eshop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ead.eshop.AppRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)

val sampleProducts = listOf(
    Product(1, "Smartphone", "Latest model", 699.99, "Electronics", "image_url_1"),
    Product(2, "Laptop", "High performance laptop", 1199.99, "Electronics", "image_url_2"),
    Product(3, "T-shirt", "Comfortable cotton", 29.99, "Fashion", "image_url_3"),
    Product(4, "Novel", "Bestselling book", 19.99, "Books", "image_url_4")
)


@Composable
fun HomeScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    navController: NavController
) {
    val categories = listOf("All", "Electronics", "Fashion", "Books")
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Logout Button
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    TokenManager.clearToken(navController.context)

                    withContext(Dispatchers.Main) {
                        navController.navigate(AppRoutes.welcomeScreen) {
                            popUpTo(AppRoutes.welcomeScreen) { inclusive = true }
                        }
                    }
                }// Call the logout function to remove token and navigate
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Text("Logout")
        }

        // Category Filter
        LazyRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Button(
                    onClick = { selectedCategory = category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            Color.Gray
                    )
                ) {
                    Text(category)
                }
            }
        }

        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Filtered Product List
        val filteredProducts = products.filter {
            (selectedCategory == "All" || it.category == selectedCategory) &&
                    it.name.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductItem(product = product, onClick = { onProductClick(product) })
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = product.name, style = MaterialTheme.typography.titleMedium)
        Text(text = "$${product.price}", style = MaterialTheme.typography.bodyLarge)
    }
}

