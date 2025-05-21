package com.arise.aquatrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arise.aquatrack.model.Project
import com.arise.aquatrack.ui.theme.DeepBlue

@Composable
fun ProjectCard(
    project: Project,
    onEditClick: (Project) -> Unit,
    onDeleteClick: (Project) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Light blue background
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(project.name, style = MaterialTheme.typography.titleMedium, color = DeepBlue)
                Text("Client: ${project.clientName}", style = MaterialTheme.typography.bodyMedium)
                Text("Location: ${project.location}", style = MaterialTheme.typography.bodyMedium)
                Text("Status: ${project.status}", style = MaterialTheme.typography.bodyMedium)
                Text("Start: ${project.startDate}  End: ${project.endDate}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                IconButton(onClick = { onEditClick(project) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = DeepBlue)
                }
                IconButton(onClick = { onDeleteClick(project) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}
