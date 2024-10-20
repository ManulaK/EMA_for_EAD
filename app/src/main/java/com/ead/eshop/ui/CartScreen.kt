package com.ead.eshop.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ead.eshop.data.model.Cart
import com.ead.eshop.data.model.CartItem
import com.ead.eshop.utils.Resource
import com.ead.eshop.viewmodels.ProductViewModel

@Composable
fun CartScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    token: String // Assuming token is passed down to this composable
) {
    // Observe cart items from the ViewModel
    val cartItems by productViewModel.cartItems.observeAsState(Resource.Loading())

    // Fetch the cart when the screen is first displayed
    LaunchedEffect(Unit) {
        productViewModel.fetchCart(token)
    }

    when (cartItems) {
        is Resource.Loading -> {
            // Show loading indicator
            CircularProgressIndicator()
        }
        is Resource.Success -> {
            val cart = (cartItems as Resource.Success).data
            // Show cart items
            CartContent(cart)
        }
        is Resource.Error -> {
            // Show error message
            val errorMessage = (cartItems as Resource.Error).message
            Text(text = errorMessage ?: "Unknown error occurred", color = Color.Red)
        }
    }
}

@Composable
fun CartContent(cart: Cart?) {
    cart?.let {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Your Cart",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(it.items) { item ->
                    CartItemView(item)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TotalPrice(totalAmount = it.totalAmount)
            CheckoutButton()
        }
    } ?: run {
        Text("Your cart is empty.", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun CartItemView(item: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Product ID: ${item.productId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quantity: ${item.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Price: $${item.price}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Total: $${item.total}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun TotalPrice(totalAmount: Double) {
    Text(
        text = "Total Amount: $${totalAmount}",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun CheckoutButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text("Confirm", color = Color.White, fontSize = 18.sp)
    }
}



