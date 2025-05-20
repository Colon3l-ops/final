package com.arise.aquatrack.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arise.aquatrack.R
import com.arise.aquatrack.navigation.ROUTE_HOME
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animate scale (pulsating effect)
    val scale = remember { Animatable(0f) }

    // Trigger animation on launch
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500, easing = EaseOutElastic)
        )
        delay(2500)
        navController.navigate(ROUTE_HOME) {
            popUpTo(0) // Remove splash screen from backstack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF001F3F),
                        Color(0xFF0074D9)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF00BFFF), Color(0xFF001F3F)),
                            radius = 200f
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.water_drop), // Add water_drop.png to res/drawable
                    contentDescription = "Water Drop Logo",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "AquaTrack",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Empowering Clean Water Projects",
                fontSize = 16.sp,
                color = Color(0xFFB3E5FC),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
