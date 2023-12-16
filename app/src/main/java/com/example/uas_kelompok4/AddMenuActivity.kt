package com.example.uas_kelompok4

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.databinding.ActivityAddMenuBinding
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMenuBinding
    private lateinit var fbRef: DatabaseReference
    private lateinit var stRef: StorageReference
    private var uri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("menu")

        stRef = FirebaseStorage.getInstance("gs://uas-kelompok-4-5e25b.appspot.com")
            .getReference("Images")

        binding.submitMenu.setOnClickListener {
            saveData()
        }
        val takeImgRes = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val fileSizeInBytes = contentResolver.openInputStream(it)?.available() ?: 0
                val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)

                val maxSizeInMB = 5.0

                if (fileSizeInMB > maxSizeInMB) {
                    Toast.makeText(this, "Image can't be more than $maxSizeInMB MB", Toast.LENGTH_LONG).show()
                } else {
                    binding.menuImageSub.setImageURI(it)
                    uri = it
                }
            }
        }
        binding.addImgButton.setOnClickListener {
            takeImgRes.launch("image/*")
        }
    }
    private fun saveData() {
        val name = binding.menuNameEt.text.toString()
        val price = binding.priceEt.text.toString().toInt()
        if(name.isEmpty()) Toast.makeText(this, "Please enter the menu item name!", Toast.LENGTH_LONG).show()
        if(price == null) Toast.makeText(this, "Please enter the price!", Toast.LENGTH_LONG).show()
        val isAppetizer = binding.chooseAppetizer
        val isMC = binding.chooseMc
        val isDrink = binding.chooseDrink
        var foodCat = "yee"
        if (isAppetizer.isChecked) foodCat = "Appetizer"
        else if (isMC.isChecked) foodCat = "Main Course"
        else if (isDrink.isChecked) foodCat = "Beverage"
        else Toast.makeText(this, "Please choose a food category!", Toast.LENGTH_LONG).show()
        val foodId = fbRef.push().key!!
        var menuItem: MenuItem
        uri?.let {
            stRef.child(foodId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
                            val imgURL = url.toString()

                            menuItem = MenuItem(foodId, name, imgURL, price, foodCat, 0)

                            fbRef.child(foodId).setValue(menuItem).addOnCompleteListener {
                                Toast.makeText(this, "New menu item successfully added", Toast.LENGTH_LONG).show()
                                finish()
                            }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                }
        }
    }


}