package com.example.uas_kelompok4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class TransactionHistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_hist)
        val fragment: Fragment = TransactionHistoryFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.transactionPart, fragment)
        ft.commit()
    }
}