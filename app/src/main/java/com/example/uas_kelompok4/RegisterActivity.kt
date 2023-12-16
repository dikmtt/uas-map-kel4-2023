package com.example.uas_kelompok4

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.databinding.ActivityAddMenuBinding
import com.example.uas_kelompok4.databinding.ActivityAddPromoBinding
import com.example.uas_kelompok4.databinding.ActivityRegisterBinding
import com.example.uas_kelompok4.model.Promo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        fbRef =
            FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users")

        binding.buttonRegister.setOnClickListener {
            saveData()
        }

        val nameEditText: EditText = findViewById(R.id.editTextName)
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val registerButton: Button = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerUser(name, email, password)
        }
    }

    private fun registerUser(email: String, password: String, name: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: ""
                    val user = mapOf(
                        "name" to name,
                        "email" to email,
                        "password" to password
                    )
                    fbRef.child(userId).setValue(user)

                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {

                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveData() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        var password = binding.editTextPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.length <= 6) {
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your E-Mail!", Toast.LENGTH_SHORT).show()
            }
            if (password.length >= 6) {
                Toast.makeText(this, "Password Must 6 ot more character!", Toast.LENGTH_SHORT)
                    .show()
            }
            return
        }
        registerUser(name, email, password)

    }
}