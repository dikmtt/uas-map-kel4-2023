package com.example.uas_kelompok4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.databinding.ActivityAddPromoBinding
import com.example.uas_kelompok4.model.Promo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPromoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPromoBinding
    private lateinit var fbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPromoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbRef = FirebaseDatabase.getInstance()
            .getReference("promo")

        binding.submitPromo.setOnClickListener {
            saveData()
        }
    }
    private fun saveData() {
        val name = binding.promoNameEt.text.toString()
        val discAmount = binding.discAmountEt.text.toString().toInt()
        val minPrice = binding.minPurchaseEt.text.toString().toInt()
        var discAmountDb: Double = 0.0
        if(name.isEmpty()) Toast.makeText(this, "Please enter the promo name!", Toast.LENGTH_LONG).show()
        if((discAmount > 100) or (discAmount < 0)) Toast.makeText(this, "Please enter a valid discount amount!", Toast.LENGTH_LONG).show()
        else {
            discAmountDb = (1 / discAmount).toDouble()
        }
        val promoId = fbRef.push().key!!
        val promo = Promo(promoId, name, discAmountDb, minPrice)
        fbRef.child(promoId).setValue(promo)
    }
}