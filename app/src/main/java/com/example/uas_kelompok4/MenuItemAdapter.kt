package com.example.uas_kelompok4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(private val layoutInflater: LayoutInflater,
                      private val menuList: java.util.ArrayList<MenuItem>)
    : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()
    private lateinit var fbRef: DatabaseReference

    class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemName: TextView by lazy {
            itemView.findViewById(R.id.item_menu_name)
        }
        val itemImg: ImageView by lazy {
            itemView.findViewById(R.id.item_img)
        }
        val itemPrice: TextView by lazy {
            itemView.findViewById(R.id.item_price)
        }
        val addItem: Button by lazy {
            itemView.findViewById(R.id.add_item)
        }
        val removeItem: Button by lazy {
            itemView.findViewById(R.id.remove_item)
        }
        val counter: TextView by lazy {
            itemView.findViewById(R.id.counter)
        }

        fun bind(menuItem: MenuItem) {
            itemName.text = menuItem.name
            itemPrice.text = menuItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            layoutInflater.inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuIt = menuList[position]
        holder.itemName.text = menuIt.name
        holder.itemPrice.text = menuIt.price.toString()
        Glide.with(holder.itemImg)
            .load(menuIt.imageUrl)
            .into(holder.itemImg)
        holder.counter.text = menuIt.boughtValue.toString()
        holder.addItem.setOnClickListener {
            val menuId = menuIt.id
            var newValue = menuIt.boughtValue
            newValue++
            fbRef.child("menu").child(menuId).child("boughtvalue")
                .setValue(newValue)
            holder.counter.text = newValue.toString()
        }
        holder.removeItem.setOnClickListener {
            val menuId = menuIt.id
            var newValue = menuIt.boughtValue
            newValue--
            fbRef.child("menu").child(menuId).child("boughtvalue")
                .setValue(newValue)
            holder.counter.text = newValue.toString()
        }
    }
}