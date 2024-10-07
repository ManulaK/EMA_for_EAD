package com.ead.eshop.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.ead.eshop.R
@Composable
fun AppBar(
    navController: NavController,
    isVisible: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNotificationIconClick: () -> Unit,
    onUserIconClick: () -> Unit,
    onOrderIconClick: () -> Unit // New callback for the order icon
) {
    if (isVisible) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            // Top Row for App Name and Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Eshop",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        letterSpacing = 3.sp
                    ),
                    modifier = Modifier.weight(1f)
                )

                // User Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .clickable { onUserIconClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "User Icon",
                        modifier = Modifier.padding(12.dp) // Added padding inside
                    )
                }
                Spacer(modifier = Modifier.width(8.dp) )
                // Order Icon
                ConstraintLayout {
                    val (order, orderCounter) = createRefs()

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .constrainAs(order) {
                                end.linkTo(parent.end)
                            }
                            .clickable { onOrderIconClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.parcel),
                            contentDescription = "Order Icon",
                            modifier = Modifier.padding(12.dp) // Added padding inside
                        )
                    }

                    // Order count
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Color.Blue, shape = CircleShape)
                            .padding(3.dp)
                            .constrainAs(orderCounter) {
                                top.linkTo(order.top)
                                end.linkTo(order.end)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "2", fontSize = 11.sp, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp) )

                // Notification Icon
                ConstraintLayout {
                    val (notification, notificationCounter) = createRefs()

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .constrainAs(notification) {
                                end.linkTo(parent.end)
                            }
                            .clickable { onNotificationIconClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bell),
                            contentDescription = "Notification Icon",
                            modifier = Modifier.padding(12.dp) // Added padding inside
                        )
                    }

                    // Notification count
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Color.Red, shape = CircleShape)
                            .padding(3.dp)
                            .constrainAs(notificationCounter) {
                                top.linkTo(notification.top)
                                end.linkTo(notification.end)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "3", fontSize = 11.sp, color = Color.White)
                    }
                }
            }

            // New Row for Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), // Adjust padding as needed
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { newText ->
                        onSearchQueryChange(newText) // Update search query
                    },
                    singleLine = true,
                    placeholder = { Text(text = "Search product") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Product Search Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .weight(1f),
                )
            }
        }
    }
}
