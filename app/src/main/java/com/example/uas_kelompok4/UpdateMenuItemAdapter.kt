package com.example.uas_kelompok4

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.database.FirebaseDatabase


class UpdateMenuItemAdapter(
    private val layoutInflater: LayoutInflater,
    private val menuList: ArrayList<MenuItem>,
    private val orderedMenu: MutableList<MenuItem>, // Add orderedMenu as a parameter
    private val updateMenuItemListener: OnUpdateMenuItemListener
) : RecyclerView.Adapter<UpdateMenuItemAdapter.UpdateMenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()
    private val fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("menu")

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
            // Pass the context from the holder's itemView
            val context = holder.itemView.context
            showUpdateMenuItemDialog(context, menuIt)
            Log.d("updateItem", menuIt.name)
        }

        holder.deleteItem.setOnClickListener {
            // Pass the context from the holder's itemView
            val context = holder.itemView.context
            showDeleteConfirmationDialog(context, menuIt)
            Log.d("deleteItem", menuIt.name)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, menuItem: MenuItem) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirm Deletion")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            val databaseRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("menu")
                .child(menuItem.id) // Assuming 'id' is the unique identifier of your MenuItem

            databaseRef.removeValue() // Delete from the database

            menuList.remove(menuItem)
            notifyDataSetChanged()
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    interface OnUpdateMenuItemListener {
        fun onUpdateMenuItem(menuItem: MenuItem)
    }
    private fun showUpdateMenuItemDialog(context: Context, menuItem: MenuItem) {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.update_menu_item_dialog, null)

        val etName = dialogView.findViewById<EditText>(R.id.editTextName)
        val etPrice = dialogView.findViewById<EditText>(R.id.editTextPrice)
        // Add other EditText fields to edit menu item details

        etName.setText(menuItem.name)
        etPrice.setText(menuItem.price.toString())
        // Set other EditText fields with respective values

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle("Edit Menu Item")

        alertDialogBuilder.setPositiveButton("Update") { _, _ ->
            val updatedName = etName.text.toString()
            val updatedPrice = etPrice.text.toString().toDouble() // Handle conversion to double

            // Update the MenuItem object with new details
            menuItem.name = updatedName
            menuItem.price = updatedPrice.toInt()

            // Update the item in Firebase Realtime Database
            val databaseRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("menu")
                .child(menuItem.id) // Assuming 'id' is the unique identifier of your MenuItem

            databaseRef.setValue(menuItem) // Update in the database

            // Notify the listener with the updated MenuItem
            updateMenuItemListener.onUpdateMenuItem(menuItem)
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}