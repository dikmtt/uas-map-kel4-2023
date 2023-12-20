package com.example.uas_kelompok4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Login with Firebase Authentication
/*
*  need user data stored in Authentication.
* */
class LoginActivity : AppCompatActivity() {

    //connect to authentication
   // private lateinit var firebaseAuth: FirebaseAuth

    //Firebase Authentication
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Login Successful")
                    // Handle success, navigate to the main screen or perform other actions
                } else {
                    showToast("Login failed: ${task.exception?.message}")
                }
            }
    }*/

    //Realtime database
    private fun loginUser(email: String, password: String) {
        val usersRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val storedPassword = userSnapshot.child("password").getValue(String::class.java)
                        val storedRole = userSnapshot.child("role").getValue(String::class.java)

                        if (storedPassword == password) {
                            showToast("Login Successful")

                            // Check user role
                            when (storedRole) {
                                "admin" -> {
                                    // Handle admin login
                                }
                                "staff" -> {
                                    // Handle staff login
                                }
                                else -> {
                                    // Handle member login
                                }
                            }

                            return
                        }
                    }

                    showToast("Incorrect password")
                } else {
                    showToast("User not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast("Database error: ${databaseError.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
