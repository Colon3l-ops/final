package com.arise.aquatrack.screens.equipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.model.Equipment
import com.arise.aquatrack.navigation.ROUTE_ADD_EQUIPMENT

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
                title = {
                    Text(
                        "All Equipment",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF64B5F6)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_ADD_EQUIPMENT)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Equipment",
                            tint = Color(0xFF64B5F6)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B1F3B),
                    titleContentColor = Color(0xFF64B5F6)
                )
            )
        },
        containerColor = Color(0xFF0A0E23)
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2746))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Name: ${equipment.name}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF90CAF9)
                        )
                        Text("Model: ${equipment.model}", color = Color(0xFFBBDEFB))
                        Text("Serial: ${equipment.serialNumber}", color = Color(0xFFBBDEFB))
                        Text("Manufacturer: ${equipment.manufacturer}", color = Color(0xFFBBDEFB))
                        Text("Year: ${equipment.year}", color = Color(0xFFBBDEFB))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewEquipmentsScreenPreview() {
    ViewEquipmentsScreen(rememberNavController())
}
