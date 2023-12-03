package com.example.uas_kelompok4

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val rvFrag = MenuFragment()
        var currFrag = rvFrag
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.order_fragments, rvFrag)
        ft.commit()

        val btn = findViewById<Button>(R.id.order_to_review_btn)
        /*btn.setOnClickListener {
            if(currFrag is MenuFragment) {
                //Transport to the review fragment
                //Another code: when it's the review fragment, move to the confirmation screen
            }
        }*/

    }

}