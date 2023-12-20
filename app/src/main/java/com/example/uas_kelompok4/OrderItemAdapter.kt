package com.example.uas_kelompok4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.MenuItem

class OrderItemAdapter (val menus: List<MenuItem>):RecyclerView.Adapter<OrderItemAdapter.OrderHolder>() {
    class OrderHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemQty = view.findViewById<TextView>(R.id.item_qty)
        val itemPrice = view.findViewById<TextView>(R.id.item_price)
        val iTotal = view.findViewById<TextView>(R.id.item_iTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return OrderHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val menuPos = menus[position]
        holder.itemName.text = menuPos.name
        holder.itemQty.text = menuPos.boughtValue.toString()
        holder.itemPrice.text = menuPos.price.toString()
        holder.iTotal.text = (menuPos.boughtValue * menuPos.price).toString()
    }
}