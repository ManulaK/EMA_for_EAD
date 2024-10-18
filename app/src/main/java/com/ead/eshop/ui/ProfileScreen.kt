package com.ead.eshop.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ead.eshop.AppRoutes
import com.ead.eshop.R
import com.ead.eshop.data.model.Address
import com.ead.eshop.data.model.UpdateRequest
import com.ead.eshop.data.repository.AuthRepository
import com.ead.eshop.viewmodels.ProfileViewModel
import com.ead.eshop.viewmodels.factorys.ProfileViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val tokenFlow = TokenManager.getToken(context).collectAsState(initial = null)
    val token = tokenFlow.value

    val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(AuthRepository()))

    // State for showing the confirmation dialog
    var showDeactivateDialog by remember { mutableStateOf(false) }

    // Load user data if token is available
    LaunchedEffect(token) {
        token?.let {
            profileViewModel.loadUserData(it)
        }
    }

    val userDetails = profileViewModel.userDetails.value

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    LaunchedEffect(userDetails) {
        userDetails?.let {
            firstName = it.firstName
            lastName = it.lastName
            dateOfBirth = it.dateOfBirth
            email = it.email
            street = it.address.street
            city = it.address.city
            state = it.address.state
            postalCode = it.address.postalCode
            country = it.address.country
        }
    }

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
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
                IconButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            TokenManager.clearToken(navController.context)

                            withContext(Dispatchers.Main) {
                                navController.navigate(AppRoutes.welcomeScreen) {
                                    popUpTo(AppRoutes.welcomeScreen) { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Hi, My name is", fontSize = 16.sp, color = Color.Gray)
            Text(text = "$firstName $lastName", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("Date of Birth") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = street,
                onValueChange = { street = it },
                label = { Text("Street") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("State") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    label = { Text("Postal Code") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Country") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val updateRequest = UpdateRequest(
                            firstName = firstName,
                            lastName = lastName,
                            dateOfBirth = dateOfBirth,
                            email = email,
                            address = Address(
                                street = street,
                                city = city,
                                state = state,
                                postalCode = postalCode,
                                country = country
                            )
                        )
                        profileViewModel.updateUserData(token ?: "", updateRequest ,context)
                        Log.d("ProfileScreen", "Updated Data: $updateRequest")
                    },
                    modifier = Modifier.
                    align(Alignment.End),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimaryContainer),
                ) {
                    Text("Save")
                }

            Spacer(modifier = Modifier.height(42.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                )
                val annotatedText = buildAnnotatedString {
                    append("Your account is currently activated. ")

                    append("For security and privacy reasons, if you no longer wish to use this account "
                            + "you can choose to temporarily or "
                            + "permanently deactivate it. ")
                    pushStringAnnotation(tag = "deactivate", annotation = "deactivate")
                    withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                        append("Click to deactivate")
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "deactivate", start = offset, end = offset)
                            .firstOrNull()?.let {
                                showDeactivateDialog = true
                            }
                    }
                )
            }

            // Deactivate confirmation dialog
            if (showDeactivateDialog) {
                AlertDialog(
                    onDismissRequest = { showDeactivateDialog = false },
                    title = { Text("Deactivate Account") },
                    text = { Text("Are you sure you want to deactivate your account? This action cannot be undone.") },
                    confirmButton = {
                        TextButton(onClick = {
                            if (token != null) {
                                Log.d("ProfileScreen", "Deactivate: $token")
                                profileViewModel.deactivateUser(token, context)
                            }
                            showDeactivateDialog = false
                            // Navigate to deactivation flow or call your deactivate logic
                        }) {
                            Text("Confirm", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDeactivateDialog = false // Close the dialog without action
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (profileViewModel.isDeactivated.value) {
                LaunchedEffect(Unit) {
                    navController.navigate(AppRoutes.loginScreen) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}