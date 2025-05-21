package com.arise.aquatrack.screens.Dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.R
import com.arise.aquatrack.navigation.ROUTE_ADD_CLIENT
import com.arise.aquatrack.navigation.ROUTE_VIEW_CLIENTS
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.Blue) {
                NavigationBarItem(
                    selected = selectedItem.intValue == 0,
                    onClick = {
                        selectedItem.intValue = 0
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download here: https://www.github.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
                    label = { Text(text = "Share") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.intValue == 1,
                    onClick = {
                        selectedItem.intValue = 1
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:0712345678".toUri()
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
                    label = { Text(text = "Phone") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.intValue == 2,
                    onClick = {
                        selectedItem.intValue = 2
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:info@emobilis.edu".toUri()
                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry")
                            putExtra(Intent.EXTRA_TEXT, "Hello, I would like to join you. Please help")
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                    label = { Text(text = "Email") },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->

        Box {
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "background image",
                modifier = Modifier
                    .height(100.dp)
                    .padding(25.dp)
                    .fillMaxSize()
                    .padding(innerPadding),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = { Text(text = "Aqua Track") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )

            Row(modifier = Modifier.wrapContentWidth()) {
                DashboardCard("Clients") { navController.navigate(ROUTE_VIEW_CLIENTS) }
                DashboardCard("Equipment") { navController.navigate("equipment") }
                DashboardCard("Projects") { navController.navigate("projects") }
            }

            Row(modifier = Modifier.wrapContentWidth()) {
                DashboardCard("Products") { navController.navigate("viewproducts") }
                DashboardCard("Staff") { navController.navigate("staff") }
                DashboardCard("Drilling Crew") { navController.navigate("crew") }
            }
        }

        // Confirmation Dialog for Exit
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Exit") },
                text = { Text("Are you sure you want to exit the app?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        (context as? android.app.Activity)?.finishAffinity()
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun DashboardCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color(0xFF0099CC))
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
