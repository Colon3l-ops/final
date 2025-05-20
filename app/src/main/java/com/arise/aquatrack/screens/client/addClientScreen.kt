//package com.arise.aquatrack.screens.client
//
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import com.arise.aquatrack.data.ClientViewModel
//import com.arise.aquatrack.ui.theme.blue2
//import com.arise.aquatrack.ui.theme.green1
//import com.arise.aquatrack.ui.theme.newwhite
//import com.arise.aquatrack.ui.theme.red
//import com.arise.aquatrack.R
//
//
//@Composable
//fun AddClientScreen(navController: NavController) {
//    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
//            uri: Uri? -> imageUri.value = uri
//    }
//
//    var name by remember { mutableStateOf("") }
//    var contact by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
//    var notes by remember { mutableStateOf("") }
//
//    val clientViewModel: ClientViewModel = viewModel()
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(blue2)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Add New Client",
//            color = Color.White,
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 24.dp)
//        )
//
//        Card(
//            shape = CircleShape,
//            modifier = Modifier
//                .size(180.dp)
//                .clickable { launcher.launch("image/*") },
//            elevation = CardDefaults.cardElevation(8.dp),
//            colors = CardDefaults.cardColors(containerColor = newwhite)
//        ) {
//            AsyncImage(
//                model = imageUri.value ?: R.drawable.ic_person,
//                contentDescription = "Client Photo",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(180.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Tap to Upload Photo", color = Color.White, fontSize = 14.sp)
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Client Name") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = contact,
//            onValueChange = { contact = it },
//            label = { Text("Contact Info") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = location,
//            onValueChange = { location = it },
//            label = { Text("Location") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = notes,
//            onValueChange = { notes = it },
//            label = { Text("Additional Notes") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp),
////            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Button(
//                onClick = { navController.popBackStack() },
//                colors = ButtonDefaults.buttonColors(containerColor = red),
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Cancel", color = Color.White)
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Button(
//                onClick = {
//                    imageUri.value?.let {
//                        clientViewModel.uploadClientWithImage(
//                            it, context, name, contact, location, notes, navController
//                        )
//                    } ?: Toast.makeText(context, "Please select a photo", Toast.LENGTH_SHORT).show()
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = green1),
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Save", color = Color.White)
//            }
//        }
//    }
//}
//
//fun TextFieldDefaults.outlinedTextFieldColors(textColor: Color): TextFieldColors {
//    return TODO("Provide the return value")
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AddClientScreenPreview() {
//    AddClientScreen(rememberNavController())
//}
//


package com.arise.aquatrack.screens.client





import android.R.attr.navigationIcon
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.arise.aquatrack.data.ClientViewModel

@Composable
fun AddClientScreen(
    navController: NavController,
    viewModel: ClientViewModel = viewModel()
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri.value = uri
    }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.uploadError.collectAsState()
//    val background = painterResource(id = R.drawable.background)
//    val GoldDark = Color(0xFFB8860B) // Dark goldenrod hex color

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        Column(
            modifier = Modifier
                .padding(16.dp)
//                .paint(
//                    painter = background,
//                    contentScale = ContentScale.Crop
//                )
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Add A New Client",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Contact") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Notes") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Select Image")
                    }
                    imageUri?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Button(
                            onClick = {
                                if (name.isNotBlank() && price.isNotBlank() && region.isNotBlank() && description.isNotBlank() && imageUri.value != null) {
                                    viewModel.uploadClientWithImage( // Use the correct function from ViewModel
                                        uri = imageUri.value!!,
                                        context = context,
                                        name = name,
                                        location = region,
                                        notes = description,
                                        contact = price,
                                        navController = navController
                                    )
                                } else {
                                    Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Upload Client")
                        }
                    }

//                    if (error != null) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Error: $error",
//                            color = MaterialTheme.colorScheme.error,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddClientScreenPreview() {
    AddClientScreen(rememberNavController())
}

