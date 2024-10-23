package com.ead.eshop.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import base64ToImageBitmap
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ead.eshop.AppRoutes
import com.ead.eshop.R
import com.ead.eshop.data.model.AddToCartRequest
import com.ead.eshop.data.model.OrderItems
import com.ead.eshop.data.model.OrderRequest
import com.ead.eshop.data.model.ProductByIdResponse
import com.ead.eshop.data.model.RateVendorRequest
import com.ead.eshop.data.model.Review
import com.ead.eshop.utils.Resource
import com.ead.eshop.viewmodels.ProductViewModel
import kotlin.math.roundToInt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    productId: String,
    productViewModel: ProductViewModel,
) {

    val context = LocalContext.current
    val tokenFlow = TokenManager.getToken(context).collectAsState(initial = null)
    val token = tokenFlow.value

    LaunchedEffect(productId, token) {
        token?.let {
            productViewModel.fetchProductById(it, productId)
        }
    }

    val productState by productViewModel.product.observeAsState()
    val colorList = listOf(Color.Red, Color.Black, Color.Blue, Color.LightGray,)
    var quantity by remember { mutableIntStateOf(1) }
    var colorSelected by remember { mutableStateOf(colorList[0]) }

    when (val productResource = productState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.size(24.dp))
            }
        }
        is Resource.Success -> {
            productResource.data?.let { product ->

                val basePrice = product.price
                val imageList = List(4) { product.image }
                var selectedPicture by remember { mutableStateOf(imageList[0]) }
                val totalPrice = basePrice * quantity
                Log.d("SEE HERE","$product")

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                        modifier = Modifier.size(300.dp),
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
                                    text = product.description.replace("\n", " ").replace("\r", " "),
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
                        ReviewScreen(product,productViewModel,token,context)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
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

                                    // Log the product details for debugging
                                    Log.d("Single Checkout", "$product")


                                    val orderItem = OrderItems(
                                        ProductId = product.id,
                                        Quantity = quantity,
                                        Price = product.price,
                                        vendorId = product.vendorId
                                    )

                                    // Create the OrderRequest with this single item
                                    val orderRequest = OrderRequest(
                                        Items = listOf(orderItem), // Single product as a list
                                        total = product.price * product.quantity  // Calculate the total price based on quantity
                                    )

                                    // Set the order request in the ViewModel
                                    productViewModel.setOrderRequest(orderRequest)

                                    // Navigate to the checkout screen
                                    navController.navigate(AppRoutes.checkoutScreen)
                                }
                            ) {
                                Text(text = "Checkout", fontSize = 16.sp)
                            }
                        }

                    }
                }
            }
        }
        is Resource.Error -> {
            // Show an error UI
            Text(text = productResource.message ?: "Error loading product")
        }
        null -> {
            // Handle null state, possibly loading or empty state
            Text(text = "No product found")
        }
    }


}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Rounded.Star else Icons.Rounded.StarBorder
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else MaterialTheme.colorScheme.surfaceContainer
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize).height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}


@Composable
fun ReviewScreen(
    productByIdResponse: ProductByIdResponse,
    productViewModel: ProductViewModel,
    token: String?,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Divider()
        VendorInfo(productByIdResponse)
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Vendor Review & Ratings",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp

        )
        Spacer(modifier = Modifier.height(16.dp))
        RatingSection(averageRating = productByIdResponse.vendor.averageRating.toDouble(), reviewCount = 23)
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        if (token != null) {
            ReviewList(productViewModel, token, productByIdResponse, context)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RatingSection(averageRating: Double, reviewCount: Int) {
    val formattedRating = String.format("%.2f", averageRating)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formattedRating, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Round the average rating to the nearest integer for stars
            val roundedRating = averageRating.roundToInt()
            repeat(5) {
                Icon(
                    painter = painterResource(
                        id = if (it < roundedRating) R.drawable.star_icon else R.drawable.star_icon
                    ),
                    contentDescription = "Star",
                    tint = Color.Yellow
                )
            }
        }
        Text(
            text = "Based on $reviewCount reviews",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        RatingBreakdown()
    }
}
@Composable
fun RatingBreakdown() {
    val ratings = listOf(
        "Excellent" to 60,
        "Good" to 20,
        "Average" to 10,
        "Below Average" to 5,
        "Poor" to 5
    )
    ratings.forEach { (label, percentage) ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, modifier = Modifier.weight(1f), fontSize = 14.sp)
            LinearProgressIndicator(
                progress = percentage / 100f,
                modifier = Modifier
                    .weight(2f)
                    .height(8.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}

@Composable
fun ReviewList(
    productViewModel: ProductViewModel,
    token: String,
    productByIdResponse: ProductByIdResponse,
    context: Context
) {
    val vendorReviews = productByIdResponse.vendor.ratingsAndComments

    // State to manage the visibility of the dialog
    var showDialog by remember { mutableStateOf(false) }
    var newRating by remember { mutableFloatStateOf(0f) }
    var newComment by remember { mutableStateOf("") }

    Column {
        vendorReviews.forEach { review ->
            Column {
                Text(
                    text = "${review.customerFirstName} ${review.customerLastName}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                // Star rating bar
                StarRatingBar(
                    rating = review.rating.toFloat(),
                    onRatingChanged = {} // Ratings are static for existing reviews
                )

                // Grey comment text
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray // Set comment color to grey
                )

                // Display human-readable createdAt date
                Text(
                    text = getFormattedTimeAgo(review.createdAt),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Row for the "Write a Review" text and add icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Review",
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Write a Review",
                color = Color.Blue,
            )
        }

        // Show the dialog when showDialog is true
        if (showDialog) {
            ReviewDialog(
                onDismiss = { showDialog = false },
                onSubmit = { rating, comment ->
                    val rateVendorRequest = RateVendorRequest(
                        Rating = rating.roundToInt(),
                        Comment = comment
                    )
                    productViewModel.rateVendor(token, rateVendorRequest, productByIdResponse.vendor.id, context)
                    newRating = 0f
                    newComment = ""
                    showDialog = false
                },
                rating = newRating,
                comment = newComment,
                onRatingChanged = { newRating = it },
                onCommentChanged = { newComment = it }
            )
        }
    }
}

// Helper function to format the "createdAt" date
fun getFormattedTimeAgo(createdAt: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val reviewDate = LocalDateTime.parse(createdAt, formatter)
    val now = LocalDateTime.now()

    val secondsAgo = ChronoUnit.SECONDS.between(reviewDate, now)
    val minutesAgo = ChronoUnit.MINUTES.between(reviewDate, now)
    val hoursAgo = ChronoUnit.HOURS.between(reviewDate, now)
    val daysAgo = ChronoUnit.DAYS.between(reviewDate, now)

    return when {
        secondsAgo < 60 -> "few seconds ago"
        minutesAgo < 60 -> "$minutesAgo minute(s) ago"
        hoursAgo < 24 -> "$hoursAgo hour(s) ago"
        daysAgo == 1L -> "one day ago"
        daysAgo < 30 -> "$daysAgo days ago"
        else -> reviewDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }
}


@Composable
fun ReviewDialog(
    onDismiss: () -> Unit,
    onSubmit: (Float, String) -> Unit,
    rating: Float,
    comment: String,
    onRatingChanged: (Float) -> Unit,
    onCommentChanged: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Add a Review", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                StarRatingBar(rating = rating, onRatingChanged = onRatingChanged)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = onCommentChanged,
                    placeholder = { Text("Write your comment here...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onSubmit(rating, comment) // Call onSubmit with the rating and comment
                    }) {
                        Text("Submit", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
        }
    }
}


@Composable
fun ReviewItem(review: Review) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(model = review.imageUrl),
            contentDescription = "User image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = review.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = review.rating.toString(), color = Color.Gray)
            }
            Text(
                text = review.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = review.comment,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun VendorInfo(productByIdResponse: ProductByIdResponse) {
    val vendorName = productByIdResponse.vendor.vendorName ?: "Unknown Vendor"
    val vendorDescription = productByIdResponse.vendor.vendorDescription ?: "No description available"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.parcel),
            contentDescription = "Shop Icon",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$vendorName (Vendor)",
                fontWeight= FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = vendorDescription,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}


