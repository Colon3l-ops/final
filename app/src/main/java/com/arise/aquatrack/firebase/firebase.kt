package com.arise.aquatrack.firebase

import com.arise.aquatrack.model.Project
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

object FirestoreService {
    private val db = FirebaseFirestore.getInstance()
    private val projectsCollection = db.collection("projects")

    fun addProject(project: Project, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        projectsCollection
            .add(project)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

//    fun getProjects(
//        onComplete: (List<Project>) -> Unit,
//        onError: (Exception) -> Unit
//    ) {
//        projectsCollection.get()
//            .addOnSuccessListener { result ->
//                val projects = result.mapNotNull { it.toObject(Project::class.java) }
//                onComplete(projects)
//            }
//            .addOnFailureListener { e -> onError(e) }
//    }
fun getProjects(
    onComplete: (List<Project>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("projects")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val projects = querySnapshot.documents.map { doc ->
                val project = doc.toObject(Project::class.java)
                project?.copy(id = doc.id) // set the document ID as the project's ID
            }.filterNotNull()
            onComplete(projects)
        }
        .addOnFailureListener { onError(it) }
}

    fun updateProject(
        project: Project,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("projects")
            .document(project.id)
            .set(project)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

//    fun deleteProject(
//        projectId: String,
//        onSuccess: () -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        db.collection("projects")
//            .document(projectId)
//            .delete()
//            .addOnSuccessListener { onSuccess() }
//            .addOnFailureListener { onFailure(it) }
//    }
fun deleteProject(
    projectId: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    if (projectId.isBlank()) {
        onFailure(IllegalArgumentException("Project ID is blank"))
        return
    }

    Firebase.firestore
        .collection("projects")
        .document(projectId)  // ✅ Correct document reference
        .delete()
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { onFailure(it) }
}


}
