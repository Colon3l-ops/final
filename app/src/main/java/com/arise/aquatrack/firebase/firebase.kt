package com.arise.aquatrack.firebase

import com.arise.aquatrack.model.Project
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreService {
    private val db = FirebaseFirestore.getInstance()
    private val projectsCollection = db.collection("projects")

    fun addProject(project: Project, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        projectsCollection
            .add(project)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getProjects(onComplete: (List<Project>) -> Unit, onError: (Exception) -> Unit) {
        projectsCollection.get()
            .addOnSuccessListener { result ->
                val projects = result.mapNotNull { it.toObject(Project::class.java) }
                onComplete(projects)
            }
            .addOnFailureListener { e -> onError(e) }
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

    fun deleteProject(
        projectId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("projects")
            .document(projectId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

}
