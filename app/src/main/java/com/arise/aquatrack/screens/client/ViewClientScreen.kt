package com.arise.aquatrack.screens.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.arise.aquatrack.data.ClientViewModel
import com.arise.aquatrack.model.ClientModel
import com.arise.aquatrack.navigation.ROUTE_ADD_CLIENT
import com.arise.aquatrack.navigation.ROUTE_UPDATE_CLIENT
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.red
import kotlinx.coroutines.delay

@Composable
fun ViewClientsScreen(navController: NavHostController, clientViewModel: ClientViewModel = ClientViewModel()) {
    val context = LocalContext.current

    // Hold clients list state
    val clients = remember { mutableStateListOf<ClientModel>() }

    // This triggers Firebase data fetch and updates clients list
    LaunchedEffect(Unit) {
        clientViewModel.viewClients(mutableStateOf(ClientModel()), clients, context)
    }

    // Animation states
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(200) // small delay before fade in
        visible = true
    }

    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            // Header Row with Back Button and Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = green1,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "All Clients",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = blue2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp)) // To balance IconButton space
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                if (clients.isEmpty()) {
                    Text(
                        "No clients found.",
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(clients) { client ->
                            ClientItem(client = client, navController = navController, clientRepository = clientViewModel)
                        }
                    }
                }
            }
        }

        // Floating Add Button
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(ROUTE_ADD_CLIENT) },
                containerColor = green1,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Client",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ClientItem(
    client: ClientModel,
    navController: NavHostController,
    clientRepository: ClientViewModel
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = blue2.copy(alpha = 0.85f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                AsyncImage(
                    model = client.imageUrl.ifBlank { "https://via.placeholder.com/120" },
                    contentDescription = "Client Image",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(end = 12.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    ClientDetail(label = "CLIENT NAME", value = client.name)
                    ClientDetail(label = "CONTACT", value = client.contact)
                    ClientDetail(label = "LOCATION", value = client.location)
                    ClientDetail(label = "NOTES", value = client.notes)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        clientRepository.deleteClient(context, client.clientId, navController)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = red),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("REMOVE", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        navController.navigate("$ROUTE_UPDATE_CLIENT/${client.clientId}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = green1),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("UPDATE", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ClientDetail(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value.ifBlank { "N/A" },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewClientScreenPreview() {
    ViewClientsScreen(rememberNavController())
}