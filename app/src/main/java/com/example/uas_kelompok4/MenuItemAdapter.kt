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

class MenuItemAdapter(
    private val layoutInflater: LayoutInflater,
    private val menuList: ArrayList<MenuItem>,
    private val orderedMenu: MutableList<MenuItem>, // Add orderedMenu as a parameter
    private val listener: OnItemChangedListener
) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()
    private val fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("menu")

    interface OnItemChangedListener {
        fun onItemChanged(menuItem: MenuItem)
    }
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

        val orderedMenuItem = orderedMenu.find { it.id == menuIt.id }

        val boughtValue = orderedMenuItem?.boughtValue ?: 0
        holder.counter.text = boughtValue.toString()

        holder.addItem.setOnClickListener {
            val foundMenuItem =
                orderedMenuItem ?: menuIt // Use the ordered item or the current item
            foundMenuItem.boughtValue++
            holder.counter.text = foundMenuItem.boughtValue.toString()
            listener.onItemChanged(foundMenuItem)
        }

        holder.removeItem.setOnClickListener {
            val foundMenuItem =
                orderedMenuItem ?: menuIt

            if (foundMenuItem.boughtValue > 0) {
                foundMenuItem.boughtValue--
                holder.counter.text = foundMenuItem.boughtValue.toString()
                listener.onItemChanged(foundMenuItem)
            }
        }
    }

//    fun setOrderItemClickListener(listener: MenuFragment.OrderItemClickListener?) {
//        orderItemClickListener = listener
//        menuItemAdapter = MenuItemAdapter(layoutInflater, menuItemList, orderedMenu, orderItemClickListener)
//        binding.menuRvFr.adapter = menuItemAdapter
//    }

}