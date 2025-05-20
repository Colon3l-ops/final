package com.arise.aquatrack.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.arise.aquatrack.model.ClientModel
import com.arise.aquatrack.navigation.ROUTE_VIEW_CLIENTS
import com.arise.aquatrack.network.ImgurService
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.UUID

class ClientViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Clients")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
fun uploadClientWithImage(
    uri: Uri,
    context: Context,
    name: String,
    location: String,
    notes: String,
    contact: String,
    navController: NavController
) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val file = getFileFromUri(context, uri)
            if (file == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

            val response = getImgurService().uploadImage(
                body,
                "Client-ID bec2e37e4bd3088"
            )

            if (response.isSuccessful) {
                val imageUrl = response.body()?.data?.link ?: ""
                ////today

                val clientId = database.push().key ?: ""
                val client = ClientModel(
                    name = name,  // Added name
                    location= location, // Added region
                    notes = notes, // Added description = description, // Added description
                    contact = contact, // Added price = price, // Changed to String
                    imageUrl = imageUrl,
                    clientId = clientId
                    // Added imageUrl

                )

                database.child(clientId).setValue(client)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
//                                loadSellerProducts()
                                navController.navigate(ROUTE_VIEW_CLIENTS)
                            }
                        }
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}



    fun viewClients(
        client: MutableState<ClientModel>,
        clients: SnapshotStateList<ClientModel>,
        context: Context
    ): SnapshotStateList<ClientModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Clients")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                clients.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(ClientModel::class.java)
                    value?.let { clients.add(it) }
                }
                if (clients.isNotEmpty()) {
                    client.value = clients.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch Clients: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        return clients
    }

    fun updateClient(
        context: Context,
        navController: NavController,
        clientId: String,
        name: String,
        contact: String,
        location: String,
        notes: String,
//        newImageUri: Uri?,
//        onComplete: (Boolean) -> Unit
    ) {
        val updates = mapOf(
            "name" to name,
            "price" to contact,
            "region" to location,
            "description" to notes
        )

//        if (newImageUri != null) {
//            val storageRef = FirebaseStorage.getInstance().reference
//                .child("client_images/${UUID.randomUUID()}")
//            storageRef.putFile(newImageUri).addOnSuccessListener {
//                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                    val updatedWithImage = updates + ("imageUrl" to uri.toString())
//                    database.child("Clients").child(clientId).updateChildren(updatedWithImage)
//                        .addOnCompleteListener { task -> onComplete(task.isSuccessful) }
//                }
//            }.addOnFailureListener {
//                onComplete(false)
//            }
//        } else {
//            database.child("products").child(clientId).updateChildren(updates)
//                .addOnCompleteListener { task -> onComplete(task.isSuccessful) }
//        }
    }

    fun deleteClient(
        context: Context,
        clientId: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete Client")
            .setMessage("Are you sure you want to delete this client?")
            .setPositiveButton("Yes") { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance().getReference("Clients/$clientId")
                databaseReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Client deleted Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Client not deleted", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
