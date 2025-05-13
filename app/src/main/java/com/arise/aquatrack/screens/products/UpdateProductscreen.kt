package com.arise.aquatrack.screens.products

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.arise.aquatrack.model.Product
import com.arise.aquatrack.ui.theme.blush
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.newwhite
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UpdateProductsScreen(navController: NavHostController, id: String) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(blush)
    )

    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val currentDataRef = FirebaseDatabase.getInstance().getReference()
        .child("Products/$id")
    currentDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val product = snapshot.getValue(Product::class.java)
            product?.let {
                name = it.name
                quantity = it.quantity
                price = it.price
                isLoading = false
            }
        }

        override fun onCancelled(error: DatabaseError) {
            isLoading = false
            errorMessage = error.message
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Update Product",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Show loading state while data is being fetched
        if (isLoading) {
            CircularProgressIndicator(color = green1)
        } else {
            // Error Message if data fetching fails
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
            }

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
                    var productName by remember { mutableStateOf(TextFieldValue(name)) }
                    var productQuantity by remember { mutableStateOf(TextFieldValue(quantity)) }
                    var productPrice by remember { mutableStateOf(TextFieldValue(price)) }

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

                    // Update Button
                    Button(
                        onClick = {
                            // Validation: Check if all fields are filled
                            if (productName.text.trim().isEmpty() || productQuantity.text.trim().isEmpty() || productPrice.text.trim().isEmpty()) {
                                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                            } else {
                                val productRepository = ProductVeiwmodel(navController, context)
                                productRepository.updateProduct(
                                    productName.text.trim(),
                                    productQuantity.text.trim(),
                                    productPrice.text.trim(),
                                    id
                                )
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = green1),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Update", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun updatePreview() {
    UpdateProductsScreen(navController = rememberNavController(), id = "123")
}
