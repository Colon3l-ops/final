package com.arise.aquatrack.data



import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.arise.aquatrack.model.Product
import com.arise.aquatrack.model.Upload
import com.arise.aquatrack.navigation.ROUTE_LOGIN

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage



class ProductVeiwmodel(var navController: NavHostController, var context: Context) {
    var authRepository: AuthViewModel

    init {
        authRepository = AuthViewModel(navController, context)
        if (!authRepository.isloggedin()) {
            navController.navigate(ROUTE_LOGIN)
        }
    }

    fun saveProduct(productName: String, productQuantity: String, productPrice: String) {
        var id = System.currentTimeMillis().toString()
        var productData = Product(productName, productQuantity, productPrice, id)
        var productRef = FirebaseDatabase.getInstance().getReference()
            .child("products/$id")
        productRef.setValue(productData).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Saving successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "ERROR: ${it.exception!!.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun viewProducts(
        product: MutableState<Product>,
        products: SnapshotStateList<Product>
    ): SnapshotStateList<Product> {
        var ref = FirebaseDatabase.getInstance().getReference().child("products")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Product::class.java)
                    product.value = value!!
                    products.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun DeleteProduct(id: String) {
        var delRef = FirebaseDatabase.getInstance().getReference()
            .child("products/$id")
        delRef.removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateProduct(name: String, quantity: String, price: String, id: String) {
        var updateRef = FirebaseDatabase.getInstance().getReference()
            .child("Products/$id")
        var updateData = Product(name, quantity, price, id)
        updateRef.setValue(updateData).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveProductWithImage(
        productName: String,
        productQuantity: String,
        productPrice: String,
        filePath: Uri
    ) {
        var id = System.currentTimeMillis().toString()
        var storageReference = FirebaseStorage.getInstance().getReference().child("Uploads/$id")

        storageReference.putFile(filePath).addOnCompleteListener {
            if (it.isSuccessful) {
                // Proceed to store other data into the db
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageUrl = it.toString()
                    var houseData = Upload(
                        productName, productQuantity,
                        productPrice, imageUrl, id
                    )
                    var dbRef = FirebaseDatabase.getInstance()
                        .getReference().child("Uploads/$id")
                    dbRef.setValue(houseData)
                    Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun ViewUploads(
        upload: MutableState<Upload>,
        uploads: SnapshotStateList<Upload>
    ): SnapshotStateList<Upload> {
        var ref = FirebaseDatabase.getInstance().getReference().child("Uploads")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uploads.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Upload::class.java)
                    upload.value = value !!
                    uploads.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return uploads
    }
}