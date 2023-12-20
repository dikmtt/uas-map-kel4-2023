package com.example.uas_kelompok4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    //private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fbRef: DatabaseReference
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Connect to Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()
        //Connect to Realtime Database
        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user")
        // when buttonRegister it should save input data on Realtime Database
        binding.buttonRegister.setOnClickListener {
            saveData()
        }
    }

    // Register user with firebase authentication, Email and Password method.
    private fun registerUser(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: ""
                    val user = mapOf(
                        "name" to name,
                        "email" to email,
                        "password" to password,
                        "role" to "member"
                    )
                    fbRef.child(userId).setValue(user)

                    showToast("Registration Successful")
                    finish()
                } else {
                    showToast("Registration failed: ${task.exception?.message}")
                }
            }
    }

    private fun saveData() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.length < 6) {
            if (name.isEmpty()) showToast("Please enter your name!")
            if (email.isEmpty()) showToast("Please enter your E-Mail!")
            if (password.length < 6) showToast("Password must be 6 or more characters!")
            return
        }

        registerUser(name, email, password)
    }*/
    // Register new user with realtime database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Realtime Database reference
        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

        binding.buttonRegister.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.length < 6) {
            if (name.isEmpty()) showToast("Please enter your name!")
            if (email.isEmpty()) showToast("Please enter your E-Mail!")
            if (password.length < 6) showToast("Password must be 6 or more characters!")
            return
        }

        val user = mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "role" to "member"
        )

        // Store user data in Realtime Database under 'user' node
        fbRef.push().setValue(user)

        showToast("Registration Successful")
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

