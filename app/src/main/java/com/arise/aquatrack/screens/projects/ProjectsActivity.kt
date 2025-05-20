package com.arise.aquatrack.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arise.aquatrack.firebase.FirestoreService
import com.arise.aquatrack.model.Project

val LightBlue = Color(0xFFE0F7FA)
val DeepBlue = Color(0xFF0077B6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(navController: NavHostController) {
    val context = LocalContext.current
    var projects by remember { mutableStateOf(listOf<Project>()) }
    var showDialog by remember { mutableStateOf(false) }

    // Load projects from Firebase once when screen launches
    LaunchedEffect(Unit) {
        FirestoreService.getProjects(
            onComplete = { projects = it },
            onError = { e ->
                Toast.makeText(context, "Failed to load: ${e.message}", Toast.LENGTH_LONG).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Projects", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = DeepBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Project")
            }
        },
        containerColor = LightBlue
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(projects) { project ->
                ProjectCard(project)
            }
        }

        if (showDialog) {
            AddProjectDialog(
                onDismiss = { showDialog = false },
                onAddProject = { newProject ->
                    FirestoreService.addProject(
                        project = newProject,
                        onSuccess = {
                            Toast.makeText(context, "Project added", Toast.LENGTH_SHORT).show()
                            showDialog = false

                            // Refresh project list after adding
                            FirestoreService.getProjects(
                                onComplete = { projects = it },
                                onError = { e ->
                                    Toast.makeText(context, "Reload error: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        onFailure = {
                            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onAddProject: (Project) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var client by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var status by remember { mutableStateOf(TextFieldValue("")) }
    var startDate by remember { mutableStateOf(TextFieldValue("")) }
    var endDate by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.text.isNotBlank() && client.text.isNotBlank()) {
                        onAddProject(
                            Project(
                                name = name.text,
                                clientName = client.text,
                                location = location.text,
                                status = status.text,
                                startDate = startDate.text,
                                endDate = endDate.text
                            )
                        )
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add New Project") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Project Name") })
                OutlinedTextField(value = client, onValueChange = { client = it }, label = { Text("Client Name") })
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") })
                OutlinedTextField(value = startDate, onValueChange = { startDate = it }, label = { Text("Start Date") })
                OutlinedTextField(value = endDate, onValueChange = { endDate = it }, label = { Text("End Date") })
            }
        }
    )
}

@Composable
fun ProjectCard(project: Project) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(project.name, style = MaterialTheme.typography.titleMedium, color = DeepBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Client: ${project.clientName}")
            Text("Location: ${project.location}")
            Text("Status: ${project.status}")
            Text("Dates: ${project.startDate} - ${project.endDate}")
        }
    }
}
