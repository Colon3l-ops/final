package com.arise.aquatrack.screens.client

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arise.aquatrack.data.ClientViewModel
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.newwhite
import com.arise.aquatrack.ui.theme.red
import com.arise.fromtheashes.R

@Composable
fun AddClientScreen(navController: NavController) {
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? -> imageUri.value = uri
    }

    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val clientViewModel: ClientViewModel = viewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blue2)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Client",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(180.dp)
                .clickable { launcher.launch("image/*") },
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = newwhite)
        ) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = "Client Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(180.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tap to Upload Photo", color = Color.White, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Client Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            label = { Text("Contact Info") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Additional Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
//            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel", color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    imageUri.value?.let {
                        clientViewModel.uploadClientWithImage(
                            it, context, name, contact, location, notes, navController
                        )
                    } ?: Toast.makeText(context, "Please select a photo", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = green1),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}

fun TextFieldDefaults.outlinedTextFieldColors(textColor: Color): TextFieldColors {
    return TODO("Provide the return value")
}

