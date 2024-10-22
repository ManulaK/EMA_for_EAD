import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ead.eshop.R


@Composable
fun CheckoutScreen(navController: NavController) {
    var cardNumber by remember { mutableStateOf("**** **** **** 6522") }
    var cardHolder by remember { mutableStateOf("Manula Kavinda") }
    var expiryDate by remember { mutableStateOf("07/23") }
    var cvc by remember { mutableStateOf("***") }
    var saveCardChecked by remember { mutableStateOf(false) }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    androidx.compose.material.IconButton(
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
                        text = "Checkout".uppercase(),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // Main content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("COMMERCIAL BANK", color = Color.White, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(cardNumber, color = Color.White, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(100.dp))
                            Text("Valid Thru $expiryDate", color = Color.White)
                            Text(cardHolder, color = Color.White)
                        }
                    }

                    // Form Fields
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            label = { Text("Card Number") },
                            visualTransformation = VisualTransformation.None,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = cardHolder,
                            onValueChange = { cardHolder = it },
                            label = { Text("Card Holder Name") },
                            modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = expiryDate,
                                onValueChange = { expiryDate = it },
                                label = { Text("Expire Date") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvc,
                                onValueChange = { cvc = it },
                                label = { Text("CVC/CVV2") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = saveCardChecked,
                            onCheckedChange = { saveCardChecked = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save your card information. It's confidential.", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Confirm Button
                    Button(
                        onClick = {


                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Pay (LKR 2353.73)", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    )
}
