package com.example.uas_kelompok4

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uas_kelompok4.model.MenuItem

class OrderActivity : AppCompatActivity(), MenuFragment.OrderItemClickListener {
    private lateinit var rvFrag: MenuFragment
    private lateinit var coFrag: ConfirmOrderFragment
    private lateinit var currFrag: Fragment
    private var isInMenu: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        rvFrag = MenuFragment.newInstance("param1", "param2")
        coFrag = ConfirmOrderFragment()
        currFrag = rvFrag
        isInMenu = 1

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.order_fragments, rvFrag)
        ft.commit()

//        rvFrag.setOrderItemClickListener(this)

        val btn = findViewById<Button>(R.id.order_to_review_btn)
        btn.setOnClickListener {
            if(isInMenu == 1) {
                rvFrag.processOrder() // Trigger the order processing logic on button click
                currFrag = coFrag
                isInMenu = 0
            } else {
                coFrag.clickButton()
            }
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
