package com.example.uas_kelompok4

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.databinding.ActivityAddMenuBinding
import com.example.uas_kelompok4.model.MenuItem
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

        fbRef = FirebaseDatabase.getInstance()
            .getReference("menu")

        stRef = FirebaseStorage.getInstance()
            .getReference("Images")

        binding.submitMenu.setOnClickListener {
            saveData()
        }
        val takeImgRes = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.menuImageSub.setImageURI(it)
            if(it != null) {
                uri = it
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
                            }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                }
        }
    }
}