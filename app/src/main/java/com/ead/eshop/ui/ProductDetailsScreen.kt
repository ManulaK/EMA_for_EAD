package com.ead.eshop.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import base64ToImageBitmap
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ead.eshop.AppRoutes
import com.ead.eshop.R
import com.ead.eshop.data.model.AddToCartRequest
import com.ead.eshop.data.model.Product
import com.ead.eshop.viewmodels.ProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    product: Product,
    productViewModel: ProductViewModel,
) {
    val context = LocalContext.current

    var quantity by remember { mutableIntStateOf(1) }

    // Define a base price and total price
    val basePrice = product.price
    val totalPrice = basePrice * quantity
    val colorList = listOf(Color.Red, Color.Black, Color.Blue, Color.LightGray,)
    var colorSelected by remember { mutableStateOf(colorList[0]) }
    val imageList = List(4) { product.image }
    var selectedPicture by remember { mutableStateOf(imageList[0]) }

    val tokenFlow = TokenManager.getToken(context).collectAsState(initial = null)
    val token = tokenFlow.value

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                Row(
                    modifier = Modifier
                        .width(70.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(3.dp)
                        .clip(RoundedCornerShape(8.dp)),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "4.5",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.star_icon),
                        contentDescription = null
                    )
                }
            }
            Image(
                bitmap = base64ToImageBitmap(product.image) ?: ImageBitmap(1, 1),
                contentDescription = null,
                modifier = Modifier.size(360.dp).padding(top = 8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(imageList.size) {
                    IconButton(
                        onClick = {
                            selectedPicture = imageList[it]
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                width = 1.dp,
                                color = if (selectedPicture == imageList[it]) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Image(
                            bitmap = base64ToImageBitmap(imageList[it]) ?: ImageBitmap(1, 1),
                            contentDescription = null,
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = product.description,
                            fontSize = 12.sp,
                            color = Color(0xFF626262)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "LKR $totalPrice",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.Red)

                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.heart_icon_2),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    Color(0x75F44336),
                                    shape = RoundedCornerShape(
                                        topStart = 20.dp,
                                        bottomStart = 20.dp
                                    )
                                )
                                .padding(10.dp)
                                .weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,

                            )
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(colorList.size) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (colorSelected == colorList[it]) Color.White else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .padding(5.dp)
                                    .background(color = colorList[it], shape = CircleShape)
                                    .clip(CircleShape)
                                    .clickable {
                                        colorSelected = colorList[it]
                                    }
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity > 1) {
                                    quantity--
                                }
                            },
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = null
                            )
                        }
                        androidx.compose.material.Text(
                            text = quantity.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(35.dp)
                                .wrapContentHeight()
                        )
                        IconButton(
                            onClick = {
                                if (quantity < 5) {
                                    quantity++
                                } else {
                                Toast.makeText(
                                    context,
                                    "You can add maximum 5 item at a time.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                }
                            },
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.plus_icon),
                                contentDescription = null
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // First Button (Add to Cart)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            val addToCartRequest = AddToCartRequest(productId = product.id, quantity = quantity)
                            if (token != null) {
                                productViewModel.addToCart(
                                    token = token,
                                    addToCartRequest = addToCartRequest,
                                    context = context,
                                    navController = navController
                                )
                            } else {
                                Toast.makeText(context, "Authentication required!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(text = "Add to Cart", fontSize = 16.sp)
                    }

                    // Second Button (Checkout)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = Color.DarkGray
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            // Handle Checkout action
                        }
                    ) {
                        Text(text = "Checkout", fontSize = 16.sp)
                    }
                }

            }
        }
    }
}
