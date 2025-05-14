package com.arise.aquatrack.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.data.AuthViewModel
//import com.arise.aquatrack.R
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.newwhite
import com.arise.aquatrack.R
import com.arise.aquatrack.ui.theme.blush

@Composable
fun RegisterScreen(navController: NavHostController) {
    val calibriFont = FontFamily(
        Font(R.font.calibril, FontWeight.Normal)
    )
    val context = LocalContext.current

    var fname by remember { mutableStateOf(TextFieldValue("")) }
    var lname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var conpass by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blue2)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id=R.drawable.img1),
            contentDescription = "Logo",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Register",
            fontSize = 30.sp,
            fontFamily = calibriFont,
            fontWeight = FontWeight.Bold,
            color = newwhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create an account",
            fontSize = 17.sp,
            fontFamily = calibriFont,
            color = newwhite
        )

        Spacer(modifier = Modifier.height(24.dp))

        InputField("First Name", fname, { fname = it }, Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Last Name", lname, { lname = it }, Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Email Address", email, { email = it }, Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Password", pass, { pass = it }, Icons.Default.Lock, isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Confirm Password", conpass, { conpass = it }, Icons.Default.Lock, isPassword = true)
        Spacer(modifier = Modifier.height(24.dp))

        // Register Button with hover/press effect
        val registerInteractionSource = remember { MutableInteractionSource() }
        val isRegisterPressed by registerInteractionSource.collectIsPressedAsState()
        val registerColor = if (isRegisterPressed) green1.copy(alpha = 0.8f) else green1

        Button(
            onClick = {
                val authViewModel = AuthViewModel(navController, context)
                authViewModel.signup(
                    fname.text.trim(),
                    lname.text.trim(),
                    email.text.trim(),
                    pass.text.trim(),
                    conpass.text.trim()
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = registerColor),
            interactionSource = registerInteractionSource
        ) {
            Text("Register", color = newwhite, modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Redirect Button with hover effect
        val loginInteractionSource = remember { MutableInteractionSource() }
        val isLoginPressed by loginInteractionSource.collectIsPressedAsState()
        val loginTextColor = if (isLoginPressed) Color.LightGray else newwhite

        TextButton(
            onClick = { navController.navigate("login") },
            interactionSource = loginInteractionSource
        ) {
            Text(
                text = "Already have an account? Log in here",
                color = loginTextColor
            )
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = newwhite) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = newwhite),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "$label icon",
                tint = newwhite
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = green1,
            unfocusedBorderColor = newwhite,
            cursorColor = green1,
            focusedLabelColor = green1,
            unfocusedLabelColor = newwhite
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
