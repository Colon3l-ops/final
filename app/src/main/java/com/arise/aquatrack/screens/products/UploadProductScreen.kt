package com.arise.aquatrack.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.data.ProductVeiwmodel
import com.arise.aquatrack.model.Upload
import com.arise.aquatrack.navigation.ROUTE_UPDATE_PRODUCT
import com.arise.aquatrack.ui.theme.blush

@Composable
fun ViewUploadsScreen(navController: NavHostController) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(blush)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val productRepository = ProductVeiwmodel(navController, context)

        val emptyUploadState = remember { mutableStateOf(Upload("", "", "", "", "")) }
        val emptyUploadsListState = remember { mutableStateListOf<Upload>() }

        val uploads = productRepository.ViewUploads(emptyUploadState, emptyUploadsListState)

        // Header Section
        Text(
            text = "All Uploads",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Uploads List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uploads) { upload ->
                UploadItem(
                    name = upload.name,
                    quantity = upload.quantity,
                    price = upload.price,
                    imageUrl = upload.imageUrl,
                    id = upload.id,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

@Composable
fun UploadItem(
    name: String,
    quantity: String,
    price: String,
    imageUrl: String,
    id: String,
    navController: NavHostController,
    productRepository: ProductVeiwmodel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Green)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 8.dp)
            )

            // Product Details
            Text(text = "Name: $name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Quantity: $quantity", fontSize = 14.sp)
            Text(text = "Price: $price", fontSize = 14.sp, color = Color.Green)

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { productRepository.DeleteProduct(id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Delete", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("$ROUTE_UPDATE_PRODUCT/$id") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Update", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
private fun UploadPreview() {
    ViewUploadsScreen(rememberNavController())
}
