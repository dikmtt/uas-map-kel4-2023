package com.example.uas_kelompok4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uas_kelompok4.model.MenuItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderActivity : AppCompatActivity(), MenuFragment.OrderItemClickListener, ConfirmOrderFragment.OrderProcessingListener {

    private lateinit var rvFrag: MenuFragment
    private lateinit var coFrag: ConfirmOrderFragment
    private lateinit var currFrag: Fragment
    private var isInMenu: Int = 1
    private lateinit var listener: ConfirmOrderFragment.OrderProcessingListener

    private var orders = ArrayList<MenuItem>() // Define orders
    private var totalItems: Int = 0 // Define totalItems
    private var totalPrice: Int = 0 // Define totalPrice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        rvFrag = MenuFragment.newInstance("param1", "param2")
        rvFrag.orderItemClickListener = this
        currFrag = rvFrag
        isInMenu = 1

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.order_fragments, rvFrag)
        ft.commit()

        coFrag = ConfirmOrderFragment.newInstance(totalItems, totalPrice, orders)
        coFrag.setOrderProcessingListener(this)

        val btn = findViewById<Button>(R.id.order_to_review_btn)
        btn.setOnClickListener {
            if(isInMenu == 1) {
                rvFrag.processOrder()
                coFrag = ConfirmOrderFragment()
                coFrag.setOrderProcessingListener(this)
                coFrag.storeValue()
                currFrag = coFrag
                isInMenu = 0
                btn.visibility = View.GONE
            } else {
                btn.visibility = View.GONE
            }
        }
    }



    override fun onOrderProcessed(orders: ArrayList<MenuItem>, totalItems: Int, totalPrice: Int) {
        this.orders = orders
        this.totalItems = totalItems
        this.totalPrice = totalPrice
        Log.d("onCreateConfirmOrderFragment", "Orders: $this.orders")
        Log.d("onCreateConfirmOrderFragment", "Total Items: $this.totalItems")
        Log.d("onCreateConfirmOrderFragment", "Total Price: $this.totalPrice")
    }
    override fun onOrderItemsSelected(orderedMenu: List<MenuItem>) {
        this@OrderActivity.orders.addAll(orderedMenu)
        totalItems = orderedMenu.size
        totalPrice = calculateTotalPrice(orderedMenu)
    }
    private fun calculateTotalPrice(orderedMenu: List<MenuItem>): Int {
        var totalPrice = 0
        for (menuItem in orderedMenu) {
            totalPrice += menuItem.price * menuItem.boughtValue
        }
        return totalPrice
    }

    override fun onItemChanged(menuItem: MenuItem) {
        Log.d("OrderActivity", "Item changed: ${menuItem.name}")
        rvFrag.updateTotalValues()
        coFrag.updateTotalValues()
    }
}