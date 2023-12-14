package com.example.uas_kelompok4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.R
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.database.*

class UpdateMenusActivity : AppCompatActivity(){
    private val fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("menu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_menu)

        val updateMenusActivity = UpdateMenusActivity()

        updateMenusActivity.retrieveMenuItems { menuItems ->
            displayMenuItems(menuItems)
        }

        if (savedInstanceState == null) {
            val fragment = UpdateDeleteMenuFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.update_menu_fragment, fragment, "UPDATE_MENU_FRAGMENT")
                .commit()
        }
    }
    fun retrieveMenuItems(callback: (List<MenuItem>) -> Unit) {
        fbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menuItems = mutableListOf<MenuItem>()

                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val menuItem = snapshot.getValue(MenuItem::class.java)
                        menuItem?.let { menuItems.add(it) }
                    }

                    // Pass the retrieved menuItems list using the callback function
                    callback(menuItems)
                } else {
                    // Handle case when there are no menu items available
                    // Example: Show a message indicating no items available
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors when data retrieval is canceled
                // Example: Show an error message or log the error
            }
        })
    }
    // Example function to display retrieved menu items
    private fun displayMenuItems(menuItems: List<MenuItem>) {
        for (menuItem in menuItems) {
            // Example: Print menu item details
            println("ID: ${menuItem.id}, Name: ${menuItem.name}, Price: ${menuItem.price}")
            // Add your logic here to use or display the menu items retrieved
        }
    }
}
