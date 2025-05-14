package com.arise.aquatrack.screens.viewClientScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.arise.aquatrack.navigation.ROUTE_UPDATE_CLIENT
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.red

@Composable
fun ViewClientsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val clientRepository = ClientViewModel()
    val emptyClientState = remember { mutableStateOf(ClientModel("", "", "", "", "", "")) }
    val clientListState = remember { mutableStateListOf<ClientModel>() }
    val clients = clientRepository.viewClients(emptyClientState, clientListState, context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Clients",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = blue2,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(clients.size) { index ->
                val client = clients[index]
                ClientItem(client = client, navController = navController, clientRepository = clientRepository)
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
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = blue2)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                AsyncImage(
                    model = client.imageUrl,
                    contentDescription = "Client Image",
                    contentScale = ContentScale.Crop,
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        clientRepository.deleteClient(context, client.clientId, navController)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("REMOVE", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        navController.navigate("$ROUTE_UPDATE_CLIENT/${client.clientId}")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = green1),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("UPDATE", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ClientDetail(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewClientsScreenPreview() {
    ViewClientsScreen(rememberNavController())
}
