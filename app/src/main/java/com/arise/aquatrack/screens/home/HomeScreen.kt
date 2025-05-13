package com.arise.aquatrack.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.navigation.ROUTE_LOGIN
import com.arise.aquatrack.navigation.ROUTE_REGISTER
//import com.arise.aquatrack.R

@Composable
fun Home_Screen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0099CC), Color(0xFF003366))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo or banner (optional)
            // Image(painter = painterResource(id = R.drawable.aquatrack_logo), contentDescription = null)

            Text(
                text = "Welcome to AquaTrack",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Efficient Borehole Management at Your Fingertips.",
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate(ROUTE_LOGIN) },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF005577))
            ) {
                Text("Login", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(ROUTE_REGISTER) },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF007799))
            ) {
                Text("Register", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    Home_Screen(rememberNavController())
}
