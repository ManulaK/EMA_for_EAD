package com.ead.eshop.ui


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import base64ToImageBitmap
import com.ead.eshop.AppRoutes
import com.ead.eshop.R
import com.ead.eshop.data.model.Cart
import com.ead.eshop.data.model.CartItem
import com.ead.eshop.utils.Resource
import com.ead.eshop.viewmodels.ProductViewModel

@Composable
fun CartScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    token: String
) {
    val cartItems by productViewModel.cartItems.observeAsState(Resource.Loading())

    LaunchedEffect(Unit) {
        productViewModel.fetchCart(token)
    }

    when (cartItems) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        is Resource.Success -> {
            val cart = (cartItems as Resource.Success).data
            Log.d("Cart Screen", cart.toString())
            CartContent(cart,navController)
        }
        is Resource.Error -> {
            val errorMessage = (cartItems as Resource.Error).message
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage ?: "Unknown error occurred",
                    color = Color.Red
                )
            }

        }
    }
}

@Composable
fun CartContent(cart: Cart?, navController: NavController) {
    cart?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = null
                    )
                }

                Text(
                    text = "Your Cart".uppercase(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(it.items) { item ->
                    CartItemView(item)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CartSummary(it.totalAmount,242.60,(it.totalAmount + 242.60))
            CheckoutButton((it.totalAmount + 242.60),navController)
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
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image section
        Box(
            modifier = Modifier
                .size(64.dp)
        ) {
            // Replace with actual image loading logic (e.g., using Coil, Glide, etc.)
            Image(
                bitmap = base64ToImageBitmap(item.productDetails.imageBase64) ?: ImageBitmap(1, 1),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

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
                text = "Price: LKR ${item.price}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Total: LKR ${item.total}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun CartSummary(subtotal: Double, shippingCost: Double, total: Double) {

    val formattedSubtotal = String.format("%.2f", subtotal)
    val formattedShippingCost = String.format("%.2f", shippingCost)
    val formattedTotal = String.format("%.2f", total)

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Subtotal", style = MaterialTheme.typography.bodyMedium)
            Text(text = "LKR $formattedSubtotal", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Shipping cost", style = MaterialTheme.typography.bodyMedium)
            Text(text = "LKR $formattedShippingCost", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "LKR $formattedTotal",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CheckoutButton(totalAmount: Double ,navController:NavController) {

    val formattedTotal = String.format("%.2f", totalAmount)
    Button(
        onClick = {
            navController.navigate(AppRoutes.checkoutScreen)
        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text("Checkout (LKR $formattedTotal)", color = Color.White, fontSize = 18.sp)
    }
}



