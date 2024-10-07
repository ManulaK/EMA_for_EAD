package com.ead.eshop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // Notifications Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Toggle Notifications */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Enable Notifications", modifier = Modifier.weight(1f))
            Switch(checked = true, onCheckedChange = { /* Toggle logic */ })
        }

        Divider()

        // Dark Mode Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Toggle Dark Mode */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Mode", modifier = Modifier.weight(1f))
            Switch(checked = false, onCheckedChange = { /* Toggle logic */ })
        }

        Divider()

        // Log Out Button
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Log Out */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log Out")
        }
    }
}
