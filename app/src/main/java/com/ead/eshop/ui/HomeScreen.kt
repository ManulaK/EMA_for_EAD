package com.ead.eshop.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import base64ToImageBitmap
import coil.compose.rememberAsyncImagePainter
import com.ead.eshop.AppRoutes
import com.ead.eshop.R
import com.ead.eshop.data.model.Category
import com.ead.eshop.data.model.Product
import com.ead.eshop.ui.components.AppBar
import com.ead.eshop.utils.Resource
import com.ead.eshop.viewmodels.ProductViewModel
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
) {


    val productState by productViewModel.products.observeAsState()
    val categoryState by productViewModel.categories.observeAsState()

    var selectedCategory by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }

    val topBarVisibilityState = remember { mutableStateOf(true) }

    val context = LocalContext.current
    val tokenFlow = TokenManager.getToken(context).collectAsState(initial = null)
    val token = tokenFlow.value

    LaunchedEffect(token) {
        token?.let {
            productViewModel.fetchCategories(token)
            productViewModel.fetchProducts(token)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        topBar = {
            AppBar(
                navController = navController,
                isVisible = topBarVisibilityState.value,
                searchQuery = searchQuery,
                onSearchQueryChange = { newQuery -> searchQuery = newQuery },
                onUserIconClick = {
                    navController.navigate(AppRoutes.profileScreen)
                },
                onNotificationIconClick = {
                    // Handle notification icon click
                },
                onOrderIconClick ={
                    navController.navigate(AppRoutes.orderScreen)
                }
            )
        },
        floatingActionButton = {
            androidx.compose.material.FloatingActionButton(
                onClick = {
                    navController.navigate(AppRoutes.cartScreen)
                },
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.cart_icon),
                    contentDescription = "Add to Cart"
                )
            }
        }
    ) { paddingValues ->
        // Main content area
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Icon Row
            Row(modifier = Modifier.fillMaxWidth()) {
                val icons = listOf(
                    Pair(R.drawable.flash_icon, "Deals"),
                    Pair(R.drawable.bill_icon, "Bill"),
                    Pair(R.drawable.game_icon, "Game"),
                    Pair(R.drawable.gift_icon, "Gifts"),
                    Pair(R.drawable.discover, "More")
                )

                icons.forEach { (iconRes, label) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = label,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { /* Handle click */ }
                                .padding(10.dp)
                        )
                        Text(text = label, fontSize = 14.sp, textAlign = TextAlign.Center)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Special Offers Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Special for you", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "See More")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Special Offer Cart
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                items(listOf(R.drawable.image_banner_3, R.drawable.image_banner_2)) { imageRes ->
                    ConstraintLayout(
                        modifier = Modifier
                            .width(280.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        val (bannerText, bannerImage) = createRefs()
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "",
                            modifier = Modifier.constrainAs(bannerImage) {}
                        )
                        Column(
                            modifier = Modifier
                                .background(Color(0x8DB3B0B0))
                                .padding(15.dp)
                                .constrainAs(bannerText) {
                                    top.linkTo(bannerImage.top)
                                    bottom.linkTo(bannerImage.bottom)
                                    start.linkTo(bannerImage.start)
                                    end.linkTo(bannerImage.end)
                                    height = Dimension.fillToConstraints
                                    width = Dimension.fillToConstraints
                                }
                        ) {
                            when (imageRes) {
                                R.drawable.image_banner_3 -> {
                                    Text(
                                        text = "Fashion",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.heightIn(15.dp))
                                    Text(text = "85 Brands", color = Color.White)
                                }
                                R.drawable.image_banner_2 -> {
                                    Text(
                                        text = "Mobile Phone & Gadget",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.heightIn(15.dp))
                                    Text(text = "15 Brands", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) { Text(text = "Popular Products", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            }
            Spacer(modifier = Modifier.height(10.dp))

            // LazyRow for Category Buttons
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (categoryState) {
                    is Resource.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        (categoryState as Resource.Error).message?.let {
                            item {
                                Text(
                                    text = it.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                    },
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                    is Resource.Success -> {
                        val categories = (categoryState as Resource.Success<List<Category>>).data ?: emptyList()
                        // Adding "All" category with an ID "all"
                        val allCategories = listOf(Category("all", "All", "")) + categories

                        items(allCategories) { category ->
                            Button(
                                onClick = { selectedCategory = category.id }, // Store category id
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                ),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                Text(
                                    text = category.name.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                    },
                                    color = if (selectedCategory == category.id) Color.Black else Color(0xFF626262),
                                    fontWeight = if (selectedCategory == category.id) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                    null -> {
                        item {
                            Text("No categories available", color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            when (productState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                is Resource.Error -> {
                    (productState as Resource.Error).message?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                is Resource.Success -> {
                    val filteredProducts = (productState as Resource.Success<List<Product>>).data?.filter { product ->
                        (selectedCategory == "all" || product.categories.any { it.id == selectedCategory }) &&
                                product.name.contains(searchQuery, ignoreCase = true)
                    }

                    if (filteredProducts.isNullOrEmpty()) {
                        Text(
                            text = "No products available",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(filteredProducts) { product ->
                                // Product item layout
                                var favouriteRemember by remember { mutableStateOf(true) }

                                Column(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .padding(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(
                                                MaterialTheme.colorScheme.surfaceContainer,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable {
                                                Log.d("Home Screen", product.id.toString())
                                                navController.navigate(AppRoutes.productDetailsScreen + "/${product.id}")
                                            }
                                    ) {
                                        Image(
                                            bitmap = base64ToImageBitmap(product.image) ?: ImageBitmap(1, 1),
                                            contentDescription = product.name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .align(Alignment.TopStart)
                                                .background(
                                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                                    shape = CircleShape
                                                )
                                                .clip(CircleShape)
                                                .clickable {
                                                    favouriteRemember = !favouriteRemember
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = if (favouriteRemember)
                                                        R.drawable.heart_icon_2
                                                    else R.drawable.heart_icon
                                                ),
                                                contentDescription = "Favourite Icon",
                                                modifier = Modifier.padding(3.dp),
                                                colorFilter = if (favouriteRemember) ColorFilter.tint(Color.Red) else null
                                            )
                                        }
                                    }

                                    Text(
                                        text = product.name,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .width(150.dp)
                                            .padding(vertical = 8.dp)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .width(150.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "LKR ${product.price}",
                                            fontWeight = FontWeight(800),
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                null -> {
                    Text(
                        text = "No products available",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
