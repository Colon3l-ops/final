//package com.arise.aquatrack.ui.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.arise.aquatrack.model.Project
//
//@Composable
//fun EditProjectDialog(
//    project: Project,
//    onDismiss: () -> Unit,
//    onUpdateProject: (Project) -> Unit
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
//        title = { Text("Edit Project") },
//        text = {
//            Column {
//                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Project Name") }, modifier = Modifier.fillMaxWidth())
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(value = clientName, onValueChange = { clientName = it }, label = { Text("Client Name") }, modifier = Modifier.fillMaxWidth())
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(value = startDate, onValueChange = { startDate = it }, label = { Text("Start Date") }, modifier = Modifier.fillMaxWidth())
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(value = endDate, onValueChange = { endDate = it }, label = { Text("End Date") }, modifier = Modifier.fillMaxWidth())
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = {
//                if (name.isNotBlank()) {
//                    val updatedProject = project.copy(
//                        name = name,
//                        clientName = clientName,
//                        location = location,
//                        status = status,
//                        startDate = startDate,
//                        endDate = endDate
//                    )
//                    onUpdateProject(updatedProject)
//                }
//            }) {
//                Text("Update")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}
package com.arise.aquatrack.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arise.aquatrack.model.Project
import java.util.*

@Composable
fun EditProjectDialog(
    project: Project,
    onDismiss: () -> Unit,
    onUpdateProject: (Project) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var name by remember { mutableStateOf(project.name) }
    var clientName by remember { mutableStateOf(project.clientName) }
    var location by remember { mutableStateOf(project.location) }
    var status by remember { mutableStateOf(project.status) }
    var startDate by remember { mutableStateOf(project.startDate) }
    var endDate by remember { mutableStateOf(project.endDate) }

    val showStartDatePicker = remember { mutableStateOf(false) }
    val showEndDatePicker = remember { mutableStateOf(false) }

    if (showStartDatePicker.value) {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                startDate = "$dayOfMonth/${month + 1}/$year"
                showStartDatePicker.value = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    if (showEndDatePicker.value) {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                endDate = "$dayOfMonth/${month + 1}/$year"
                showEndDatePicker.value = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Project") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Project Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = clientName, onValueChange = { clientName = it }, label = { Text("Client Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = startDate,
                    onValueChange = {},
                    label = { Text("Start Date") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showStartDatePicker.value = true }
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = endDate,
                    onValueChange = {},
                    label = { Text("End Date") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showEndDatePicker.value = true }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    val updatedProject = project.copy(
                        name = name,
                        clientName = clientName,
                        location = location,
                        status = status,
                        startDate = startDate,
                        endDate = endDate
                    )
                    onUpdateProject(updatedProject)
                }
            }) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

