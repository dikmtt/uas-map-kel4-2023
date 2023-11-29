package com.example.uas_kelompok4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.MenuItem

class MenuItemAdapter(private val layoutInflater: LayoutInflater)
    : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()

    fun setData(test: List<MenuItem>) {
        menuItems.clear()
        menuItems.addAll(test)
        notifyDataSetChanged()
    }

    class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView by lazy {
            itemView.findViewById(R.id.item_menu_name)
        }
        private val itemImg: ImageView by lazy {
            itemView.findViewById(R.id.item_img)
        }
        private val itemPrice: TextView by lazy {
            itemView.findViewById(R.id.item_price)
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
        return menuItems.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }
}