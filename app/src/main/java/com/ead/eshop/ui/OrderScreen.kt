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
import com.ead.eshop.data.model.OrderResponse
import com.ead.eshop.data.model.OrderResponseItem
import com.ead.eshop.utils.Resource
import com.ead.eshop.viewmodels.ProductViewModel

@Composable
fun OrderScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    token: String
) {
    val orders by productViewModel.orders.observeAsState(Resource.Loading())

    LaunchedEffect(Unit) {
        productViewModel.fetchOrders(token)
    }

    when (orders) {
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
            val orderData = (orders as Resource.Success).data
            Log.d("Cart Screen", orders.toString())
            OrderContent(orderData,navController)
        }
        is Resource.Error -> {
            val errorMessage = (orders as Resource.Error).message
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
fun OrderContent(ordersResponse: List<OrderResponse>?, navController: NavController) {
    ordersResponse?.let {
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
                    text = "Your Orders".uppercase(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                // Loop through orders and display each order's items
                items(it) { order ->
                    order.items.forEach { item ->
                        OrderItemView(item)
                    }
                }
            }
        }
    } ?: run {
        Text("Your cart is empty.", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun OrderItemView(item: OrderResponseItem) {
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
            Image(
                bitmap = base64ToImageBitmap(item.imageBase64) ?: ImageBitmap(1, 1),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${item.productName}",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "Quantity: ${item.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Price: LKR ${item.price}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Order status as a tag
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(
                        color = when (item.status) {
                            "Pending" -> MaterialTheme.colorScheme.secondary
                            "Delivered" -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.status,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

