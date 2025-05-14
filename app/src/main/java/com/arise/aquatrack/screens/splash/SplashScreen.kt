package com.arise.aquatrack.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.arise.aquatrack.R
import com.arise.aquatrack.R
import com.arise.aquatrack.navigation.ROUTE_HOME
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.newwhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var progress by remember { mutableStateOf(0f) }

    // Animate the loading over 3 seconds
    LaunchedEffect(Unit) {
        val totalSteps = 60
        repeat(totalSteps) {
            progress = it / totalSteps.toFloat()
            delay(50) // 60 * 50ms = 3000ms (3 seconds)
        }
        progress = 1f
        delay(200)
        navController.navigate(ROUTE_HOME) {
            popUpTo("splashscreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blue2),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "Splash Logo",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Loading Percentage
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = newwhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Linear Progress Bar
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = green1,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
