package com.example.uas_kelompok4

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.database.FirebaseDatabase

class UpdateMenuItemAdapter(
    private val layoutInflater: LayoutInflater,
    private val menuList: ArrayList<MenuItem>,
    private val orderedMenu: MutableList<MenuItem>, // Add orderedMenu as a parameter
    private val listener: OnItemChangedListener
) : RecyclerView.Adapter<UpdateMenuItemAdapter.UpdateMenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()
    private val fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("menu")


    interface OnItemChangedListener {
        fun onItemChanged(menuItem: MenuItem)
    }
    class UpdateMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemName: TextView by lazy {
            itemView.findViewById(R.id.item_menu_name)
        }
        val itemImg: ImageView by lazy {
            itemView.findViewById(R.id.item_img)
        }
        val itemPrice: TextView by lazy {
            itemView.findViewById(R.id.item_price)
        }
        val updateItem: Button by lazy {
            itemView.findViewById(R.id.update_item)
        }
        val deleteItem: Button by lazy {
            itemView.findViewById(R.id.delete_item)
        }
//        val counter: TextView by lazy {
//            itemView.findViewById(R.id.counter)
//        }

        fun bind(menuItem: MenuItem) {
            itemName.text = menuItem.name
            itemPrice.text = menuItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateMenuViewHolder {
        val view =
            layoutInflater.inflate(R.layout.menu_itemupdatedelete, parent, false)
        return UpdateMenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: UpdateMenuViewHolder, position: Int) {
        val menuIt = menuList[position]

        holder.itemName.text = menuIt.name
        holder.itemPrice.text = menuIt.price.toString()
        Glide.with(holder.itemImg)
            .load(menuIt.imageUrl)
            .into(holder.itemImg)

        val orderedMenuItem = orderedMenu.find { it.id == menuIt.id }

//        val boughtValue = orderedMenuItem?.boughtValue ?: 0
//        holder.counter.text = boughtValue.toString()

        holder.updateItem.setOnClickListener {
            Log.d("updateItem", menuIt.name)
        }

        holder.deleteItem.setOnClickListener {
            Log.d("deleteItem", menuIt.name)
        }
    }
}