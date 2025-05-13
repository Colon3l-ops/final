package com.arise.aquatrack.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.arise.aquatrack.R
import com.arise.fromtheashes.R
import com.arise.aquatrack.data.AuthViewModel
import com.arise.aquatrack.navigation.ROUTE_DASHBOARD

@Composable
fun Login_Screen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF0077B6)), // AquaTrack's blue theme
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.img1),
            contentDescription = "AquaTrack logo",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Text(
            text = "Login",
            color = Color.White,
            fontSize = 40.sp,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = "Welcome back! Please log in to continue.",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        var email by remember { mutableStateOf(TextFieldValue("")) }
        var pass by remember { mutableStateOf(TextFieldValue("")) }
        val context = LocalContext.current

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                val authViewModel = AuthViewModel(navController, context)
                authViewModel.login(email.text.trim(), pass.text.trim())
                navController.navigate(ROUTE_DASHBOARD) // Navigate to the Dashboard after login
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B4D8)) // Blue color for button
        ) {
            Text(text = "Login", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register link
        TextButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = "Don't have an account? Register here",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    Login_Screen(navController = rememberNavController())
}
