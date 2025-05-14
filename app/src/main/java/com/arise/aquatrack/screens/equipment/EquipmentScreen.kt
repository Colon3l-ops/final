package com.arise.aquatrack.screens.equipment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.model.Equipment
import com.arise.aquatrack.screens.viewClientScreen.ViewClientsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEquipmentsScreen(navController: NavController) {
    val equipmentList = listOf(
        Equipment("Drill Rig 2000", "Model X", "SN123456", "HydroTech", 2021),
        Equipment("PumpMaster 500", "Model P", "SN654321", "AquaWorks", 2020),
        Equipment("GeoScanner", "GS-1", "SN000111", "GeoSystems", 2022)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Equipment") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(equipmentList) { equipment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFe3f2fd))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Name: ${equipment.name}", fontWeight = FontWeight.Bold)
                        Text("Model: ${equipment.model}")
                        Text("Serial: ${equipment.serialNumber}")
                        Text("Manufacturer: ${equipment.manufacturer}")
                        Text("Year: ${equipment.year}")
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewEquipmentsScreenPreview() {
    ViewClientsScreen( rememberNavController())
}
