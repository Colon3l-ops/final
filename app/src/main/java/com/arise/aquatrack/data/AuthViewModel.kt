package com.arise.aquatrack.data


import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.arise.aquatrack.model.User
import com.arise.aquatrack.navigation.ROUTE_ADD_CLIENT
import com.arise.aquatrack.navigation.ROUTE_LOGIN
import com.arise.aquatrack.navigation.ROUTE_REGISTER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel (
    var navController: NavHostController,
    var context: Context) {
    var mAuth: FirebaseAuth=FirebaseAuth.getInstance()
    fun signup(fname:String,lname:String,email:String, pass:String,conpass:String) {
        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || pass.isEmpty() || conpass.isEmpty()) {
            Toast.makeText(context, "Please fill in all details", Toast.LENGTH_LONG).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                  val userdata=User(fname,lname,email,pass,conpass,mAuth.currentUser!!.uid)
                  val regRef= FirebaseDatabase.getInstance().getReference().child("Users/"+mAuth.currentUser!!.uid)
                  regRef.setValue(userdata)
                      .addOnCompleteListener {
                          if (it.isSuccessful){
                              Toast.makeText(context,("Registered Successfully"),Toast.LENGTH_LONG).show()
                              navController.navigate(ROUTE_LOGIN)

                          }else{
                              Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()

                          }
                      }
                }
                else{
                    navController.navigate(ROUTE_REGISTER)
                }
                if (pass != conpass) {
                    Toast.makeText(context, "Password do not match", Toast.LENGTH_LONG).show()

                }
            }
        }}
    fun login(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Please fill in all details", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_ADD_CLIENT)
                } else {
                    Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_LOGIN)
                }

            }

        }

    }


        fun logout(){
            mAuth.signOut()
            navController.navigate(ROUTE_LOGIN)
        }
        fun isloggedin():Boolean{
            return mAuth.currentUser !=null
    }


}


