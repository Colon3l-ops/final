package com.arise.aquatrack.screens.products

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.data.ProductVeiwmodel
import com.arise.aquatrack.navigation.ROUTE_VIEW_PRODUCT
import com.arise.aquatrack.ui.theme.green1
//import com.arise.aquatrack.ui.theme.blue1
import com.arise.aquatrack.ui.theme.newwhite

@Composable
fun AddProductsScreen(navController: NavHostController) {
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(green1) // AquaTrack's blue theme
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Add Inventory",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Form Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = newwhite)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var productName by remember { mutableStateOf(TextFieldValue("")) }
                var productQuantity by remember { mutableStateOf(TextFieldValue("")) }
                var productPrice by remember { mutableStateOf(TextFieldValue("")) }

                // Product Name Field
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text(text = "Product Name *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Quantity Field
                OutlinedTextField(
                    value = productQuantity,
                    onValueChange = { productQuantity = it },
                    label = { Text(text = "Product Quantity *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Price Field
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text(text = "Product Price *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(onClick = {
                    // Save the product logic here
                    var productRepository = ProductVeiwmodel(navController, context)
                    productRepository.saveProduct(
                        productName.text.trim(),
                        productQuantity.text.trim(),
                        productPrice.text
                    )
                    navController.navigate(ROUTE_VIEW_PRODUCT) // Navigate to the product view screen

                },
                    colors = ButtonDefaults.buttonColors(containerColor = green1), // AquaTrack green
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Image Picker Section
                ImagePicker(
                    modifier = Modifier.fillMaxWidth(),
                    context = context,
                    navController = navController,
                    name = productName.text.trim(),
                    quantity = productQuantity.text.trim(),
                    price = productPrice.text.trim()
                )
            }
        }
    }
}

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavHostController,
    name: String,
    quantity: String,
    price: String
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = modifier) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            Box {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
            ) {
                Text(
                    text = "Select Image",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                // Upload logic here
                var productRepository = ProductVeiwmodel(navController, context)
                productRepository.saveProductWithImage(name, quantity, price, imageUri!!)
            }) {
                Text(text = "Upload", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun AddProductPreview() {
    AddProductsScreen(navController = rememberNavController())
}
