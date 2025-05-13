package com.arise.aquatrack.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.data.ProductVeiwmodel
import com.arise.aquatrack.model.Product
import com.arise.aquatrack.navigation.ROUTE_UPDATE_PRODUCT
import com.arise.aquatrack.ui.theme.blue2
import com.arise.aquatrack.ui.theme.green1
import com.arise.aquatrack.ui.theme.newwhite
import com.arise.aquatrack.ui.theme.red

@Composable
fun ViewProductsScreen(navController: NavHostController) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(blue2)
    )

    val context = LocalContext.current
    val productRepository = ProductVeiwmodel(navController, context)

    val emptyProductState = remember { mutableStateOf(Product("", "", "", "")) }
    val emptyProductsListState = remember { mutableStateListOf<Product>() }

    val products = productRepository.viewProducts(emptyProductState, emptyProductsListState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen Header
        Text(
            text = "All Products",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product List Display
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductItemCard(
                    product = product,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

@Composable
fun ProductItemCard(
    product: Product,
    navController: NavHostController,
    productRepository: ProductVeiwmodel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = newwhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Product Details
            Text(
                text = "Name: ${product.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Quantity: ${product.quantity}",
                fontSize = 14.sp
            )
            Text(
                text = "Price: ${product.price}",
                fontSize = 14.sp,
                color = green1
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { productRepository.DeleteProduct(product.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = red),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Delete", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("$ROUTE_UPDATE_PRODUCT/${product.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = green1),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Update", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun ViewProductsPreview() {
    ViewProductsScreen(rememberNavController())
}
