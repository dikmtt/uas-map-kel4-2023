package com.example.uas_kelompok4

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.example.uas_kelompok4.model.MenuItem

class UpdateMenuItemDialog(private val context: Context) {

    fun showUpdateMenuItemDialog(menuItem: MenuItem, updateListener: UpdateMenuItemAdapter.OnUpdateMenuItemListener) {
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

            // Notify the listener with the updated MenuItem
            updateListener.onUpdateMenuItem(menuItem)
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
