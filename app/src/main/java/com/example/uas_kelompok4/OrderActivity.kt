package com.example.uas_kelompok4

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.model.MenuItem

class OrderActivity : AppCompatActivity(), MenuFragment.OrderItemClickListener {
    private lateinit var rvFrag: MenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        rvFrag = MenuFragment.newInstance("param1", "param2")

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.order_fragments, rvFrag)
        ft.commit()

//        rvFrag.setOrderItemClickListener(this)

        val btn = findViewById<Button>(R.id.order_to_review_btn)
        btn.setOnClickListener {
            rvFrag.processOrder() // Trigger the order processing logic on button click
        }
    }

    override fun onOrderItemsSelected(orderedMenu: List<MenuItem>) {
        // Handle the list of ordered items received from MenuFragment
        // Proceed with the logic to navigate to the review fragment or next screen
    }

    override fun onItemChanged(menuItem: MenuItem) {
        Log.d("OrderActivity", "Item changed: ${menuItem.name}")
        rvFrag.updateTotalValues()
    }

}
