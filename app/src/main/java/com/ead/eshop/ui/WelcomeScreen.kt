package com.ead.eshop.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ead.eshop.AppRoutes
import com.ead.eshop.R

@Composable
fun WelcomeScreen(navController: NavController) {
    // Get device screen width and height
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Main content centered vertically based on screen height
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = painterResource(R.drawable.welcome2),
                contentDescription = "App Logo",
                modifier = Modifier.size((screenWidth * 2.5f).coerceAtMost(350.dp)),

            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to E-Shop",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = (screenWidth.value * 0.07f).sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text ="Discover the best products and deals at your fingertips." +
                        "Shop from a wide range of categories," +
                        " Whether you're looking for the latest trends or exclusive discounts," +
                        " we've got something for everyone. Start your shopping journey now and experience convenience like never before!"
                ,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 25.sp
                ),

                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        // Buttons at the bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate(AppRoutes.loginScreen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.07f).coerceAtMost(50.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(AppRoutes.registerScreen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.1f).coerceAtMost(50.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }

    }
}
