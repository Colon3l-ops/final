package com.arise.aquatrack.screens.products

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEquipmentScreen(navController: NavController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var model by remember { mutableStateOf(TextFieldValue("")) }
    var serial by remember { mutableStateOf(TextFieldValue("")) }
    var manufacturer by remember { mutableStateOf(TextFieldValue("")) }
    var year by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance()
    val equipmentRef = database.getReference("equipments")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Equipment", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B1F3B),
                    titleContentColor = Color(0xFF64B5F6)
                )
            )
        },
        containerColor = Color(0xFF0A0E23)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            @Composable
            fun styledTextField(
                value: TextFieldValue,
                onValueChange: (TextFieldValue) -> Unit,
                label: String,
                keyboardOptions: KeyboardOptions = KeyboardOptions.Default
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(label, color = Color(0xFF90CAF9)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color(0xFF3C4A70),
                        containerColor = Color(0xFF1E2746)
                    ),
                    keyboardOptions = keyboardOptions
                )
            }

            styledTextField(name, { name = it }, "Equipment Name")
            styledTextField(model, { model = it }, "Model")
            styledTextField(serial, { serial = it }, "Serial Number")
            styledTextField(manufacturer, { manufacturer = it }, "Manufacturer")
            styledTextField(
                year,
                { year = it },
                "Year of Manufacture",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    if (
                        name.text.isNotBlank() &&
                        model.text.isNotBlank() &&
                        serial.text.isNotBlank() &&
                        manufacturer.text.isNotBlank() &&
                        year.text.isNotBlank()
                    ) {
                        val equipmentId = UUID.randomUUID().toString()
                        val equipment = mapOf(
                            "id" to equipmentId,
                            "name" to name.text,
                            "model" to model.text,
                            "serialNumber" to serial.text,
                            "manufacturer" to manufacturer.text,
                            "year" to year.text.toIntOrNull()
                        )

                        if (equipment["year"] == null) {
                            Toast.makeText(context, "Year must be a number", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        equipmentRef.child(equipmentId).setValue(equipment)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Equipment added successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate("equipment")
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to add equipment", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF64B5F6),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Equipment", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

private fun TextFieldDefaults.outlinedTextFieldColors(
    textColor: Color,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    containerColor: Color
): TextFieldColors {
    return TODO("Provide the return value")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddEquipmentsScreenPreview() {
    AddEquipmentScreen(rememberNavController())
}
