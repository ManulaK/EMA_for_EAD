package com.ead.eshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ead.eshop.R
import com.ead.eshop.data.model.Product

@Composable
fun CartScreen(
    cartItems: List<Pair<Product, Int>>,
    onCheckout: () -> Unit,
    onProductClick: (Product) -> Unit,
    onRemoveItem: (Product) -> Unit,
    navController: NavController
) {
    val totalPrice = cartItems.sumOf { it.first.price * it.second }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
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
                text = "View Cart",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp) // Align text with the back button
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(cartItems) { (product, quantity) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onProductClick(product) },
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Product Image
                        AsyncImage(
                            model = product.image,
                            contentDescription = product.name,
                            modifier = Modifier.size(60.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Space between image and text

                        // Column for Title, Quantity, and Price
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ensures the column takes up available space
                                .padding(start = 8.dp)
                        ) {
                            // Full Product Title
                            Text(
                                text = product.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp)) // Space between title and other details

                            Text(
                                text = "\$${product.price * quantity}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        // Remove Item Button
                        IconButton(onClick = { onRemoveItem(product) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove Item", tint = Color.Gray)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Offers / Coupons Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Open Coupons / Offers */ },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Avail Offers/ Coupons", fontWeight = FontWeight.SemiBold)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Go to Coupons")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Price Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Item Amount")
                    Text(text = "\$${totalPrice}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Discount")
                    Text(text = "\$0.00") // You can update this dynamically
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Amount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "\$${totalPrice}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkout Button
        Button(
            onClick = { onCheckout() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()     .height(50.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(text = "Checkout", color = Color.White, fontSize = 18.sp)
        }
    }
}

