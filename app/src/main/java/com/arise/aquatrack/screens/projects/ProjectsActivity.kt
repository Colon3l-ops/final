//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.arise.aquatrack.ui.screens
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.arise.aquatrack.model.Project
//import com.arise.aquatrack.ui.theme.DeepBlue
//import com.arise.aquatrack.firebase.FirestoreService
//
//@Composable
//fun ProjectsScreen(navController: NavController) {
//    var projects by remember { mutableStateOf(listOf<Project>()) }
//    var showEditDialog by remember { mutableStateOf(false) }
//    var projectToEdit by remember { mutableStateOf<Project?>(null) }
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        FirestoreService.getProjects(
//            onComplete = { projects = it },
//            onError = { e ->
//                Toast.makeText(context, "Failed to load: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Projects", color = Color.White) },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = DeepBlue
//                )
//            )
//        },
//        content = { padding ->
//            Column(
//                modifier = Modifier
//                    .padding(padding)
//                    .padding(16.dp)
//            ) {
//                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                    items(projects) { project ->
//                        ProjectCard(
//                            project = project,
//                            onEditClick = {
//                                projectToEdit = it
//                                showEditDialog = true
//                            },
//                            onDeleteClick = {
//                                FirestoreService.deleteProject(it.id,
//                                    onSuccess = {
//                                        Toast.makeText(context, "Deleted: ${it.name}", Toast.LENGTH_SHORT).show()
//                                        FirestoreService.getProjects(
//                                            onComplete = { projects = it },
//                                            onError = { error ->
//                                                Toast.makeText(context, "Reload error: ${error.message}", Toast.LENGTH_SHORT).show()
//                                            }
//                                        )
//                                    },
//                                    onFailure = {
//                                        Toast.makeText(context, "Delete failed: ${it.message}", Toast.LENGTH_SHORT).show()
//                                    }
//                                )
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    )
//
//    if (showEditDialog && projectToEdit != null) {
//        EditProjectDialog(
//            project = projectToEdit!!,
//            onDismiss = { showEditDialog = false },
//            onSave = { updatedProject ->
//                FirestoreService.updateProject(updatedProject,
//                    onSuccess = {
//                        Toast.makeText(context, "Updated: ${updatedProject.name}", Toast.LENGTH_SHORT).show()
//                        FirestoreService.getProjects(
//                            onComplete = { projects = it },
//                            onError = {}
//                        )
//                        showEditDialog = false
//                    },
//                    onFailure = {
//                        Toast.makeText(context, "Update failed: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }
//        )
//    }
//}
//
//@Composable
//fun ProjectCard(
//    project: Project,
//    onEditClick: (Project) -> Unit,
//    onDeleteClick: (Project) -> Unit
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(project.name, style = MaterialTheme.typography.titleMedium, color = DeepBlue)
//            Text("Client: ${project.clientName}")
//            Text("Location: ${project.location}")
//            Text("Status: ${project.status}")
//            Text("Dates: ${project.startDate} - ${project.endDate}")
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Button(
//                    onClick = { onEditClick(project) },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
//                ) {
//                    Text("Edit")
//                }
//                Button(
//                    onClick = { onDeleteClick(project) },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
//                ) {
//                    Text("Delete")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EditProjectDialog(
//    project: Project,
//    onDismiss: () -> Unit,
//    onSave: (Project) -> Unit
//) {
//    var name by remember { mutableStateOf(project.name) }
//    var clientName by remember { mutableStateOf(project.clientName) }
//    var location by remember { mutableStateOf(project.location) }
//    var status by remember { mutableStateOf(project.status) }
//    var startDate by remember { mutableStateOf(project.startDate) }
//    var endDate by remember { mutableStateOf(project.endDate) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            TextButton(onClick = {
//                onSave(
//                    project.copy(
//                        name = name,
//                        clientName = clientName,
//                        location = location,
//                        status = status,
//                        startDate = startDate,
//                        endDate = endDate
//                    )
//                )
//            }) {
//                Text("Save")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        },
//        title = { Text("Edit Project") },
//        text = {
//            Column {
//                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Project Name") })
//                OutlinedTextField(value = clientName, onValueChange = { clientName = it }, label = { Text("Client Name") })
//                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
//                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") })
//                OutlinedTextField(value = startDate, onValueChange = { startDate = it }, label = { Text("Start Date") })
//                OutlinedTextField(value = endDate, onValueChange = { endDate = it }, label = { Text("End Date") })
//            }
//        }
//    )
//}
package com.arise.aquatrack.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arise.aquatrack.model.Project
import com.arise.aquatrack.firebase.FirestoreService
import com.arise.aquatrack.ui.components.AddProjectDialog
import com.arise.aquatrack.ui.components.EditProjectDialog
import com.arise.aquatrack.ui.components.ProjectCard
import com.arise.aquatrack.ui.theme.DeepBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(navController: NavController) {
    val context = LocalContext.current
    var projects by remember { mutableStateOf(listOf<Project>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingProject by remember { mutableStateOf<Project?>(null) }

    // Load projects from Firestore
    LaunchedEffect(Unit) {
        FirestoreService.getProjects(
            onComplete = { projects = it },
            onError = {
                Toast.makeText(context, "Failed to load: ${it.message}", Toast.LENGTH_LONG).show()
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
                onClick = { showAddDialog = true },
                containerColor = DeepBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Project", tint = Color.White)
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(projects) { project ->
                    ProjectCard(
                        project = project,
                        onEditClick = { editingProject = it },
                        onDeleteClick = {
                            FirestoreService.deleteProject(it.id,
                                onSuccess = {
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                    FirestoreService.getProjects(
                                        onComplete = { projects = it },
                                        onError = {}
                                    )
                                },
                                onFailure = {
                                    Toast.makeText(context, "Delete failed: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }
            }
        }
    )

    // Add Project Dialog
    if (showAddDialog) {
        AddProjectDialog(
            onDismiss = { showAddDialog = false },
            onAddProject = { newProject ->
                FirestoreService.addProject(
                    newProject,
                    onSuccess = {
                        Toast.makeText(context, "Project Added", Toast.LENGTH_SHORT).show()
                        FirestoreService.getProjects(
                            onComplete = { projects = it },
                            onError = {}
                        )
                        showAddDialog = false
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to add: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }

    // Edit Project Dialog
    editingProject?.let { projectToEdit ->
        EditProjectDialog(
            project = projectToEdit,
            onDismiss = { editingProject = null },
            onUpdateProject = { updatedProject ->
                FirestoreService.updateProject(
                    updatedProject,
                    onSuccess = {
                        Toast.makeText(context, "Project Updated", Toast.LENGTH_SHORT).show()
                        FirestoreService.getProjects(
                            onComplete = { projects = it },
                            onError = {}
                        )
                        editingProject = null
                    },
                    onFailure = {
                        Toast.makeText(context, "Update failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }
}
