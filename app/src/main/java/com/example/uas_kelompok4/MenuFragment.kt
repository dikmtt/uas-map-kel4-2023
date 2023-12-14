package com.example.uas_kelompok4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas_kelompok4.databinding.FragmentMenuBinding
import com.example.uas_kelompok4.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() ,MenuItemAdapter.OnItemChangedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private var menuItemList = ArrayList<MenuItem>()
    private var orderedMenu = mutableListOf<MenuItem>()
    private lateinit var fbRef: DatabaseReference
    private lateinit var fbRef1: DatabaseReference
    private var menuItemAdapter: MenuItemAdapter? = null
    private var orderItemClickListener: OrderItemClickListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onItemChanged(menuItem: MenuItem) {
        // Update the list of ordered items when the quantity changes
        val existingItem = orderedMenu.find { it.id == menuItem.id }

        if (existingItem != null) {
            existingItem.boughtValue = menuItem.boughtValue
        } else {
            // Add the selected item to the orderedMenu list
            orderedMenu.add(menuItem)
        }

        updateTotalValues() // Update the total values when items are changed
    }


    fun updateTotalValues() {
        var totalItem = 0
        var totalPrice = 0

        for (selectedItem in orderedMenu) {
            totalPrice += selectedItem.price * selectedItem.boughtValue
            totalItem += selectedItem.boughtValue
        }
        Log.d("MenuFragment", "Total Items: $totalItem, Total Price: $totalPrice")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("menu")

        // Inside onCreateView method of MenuFragment
        menuItemAdapter = MenuItemAdapter(layoutInflater, menuItemList, orderedMenu, this)

        binding.menuRvFr.adapter = menuItemAdapter
        retrieveFromDB(menuItemAdapter!!)

        binding.menuRvFr.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context, 2)
        }
//        orderItemClickListener?.let { setOrderItemClickListener(it) }


        return binding.root
    }
//    fun setOrderItemClickListener(listener: OrderItemClickListener?) {
//        orderItemClickListener = listener
//        menuItemAdapter?.setOrderItemClickListener(listener)
//    }

    private fun retrieveFromDB(menuItemAdapter: MenuItemAdapter) {
        fbRef.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItemList.clear()
                if(snapshot.exists()) {
                    for(menuSnap in snapshot.children) {
                        val menuIt = menuSnap.getValue(MenuItem::class.java)
                        menuItemList.add(menuIt!!)
                    }
                }
                binding.menuRvFr.adapter = menuItemAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun processOrder() {
        val orders = mutableListOf<MenuItem>()
        var totalItem = 0
        var totalPrice = 0
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        for (selectedItem in orderedMenu) {
            val order = MenuItem(
                selectedItem.id,
                selectedItem.name,
                selectedItem.imageUrl,
                selectedItem.price,
                selectedItem.category,
                selectedItem.boughtValue,
            )
            orders.add(order)
            totalPrice += selectedItem.price * selectedItem.boughtValue
            totalItem += selectedItem.boughtValue
        }

        if (orderedMenu.isNotEmpty()) {
            val transactionMap = HashMap<String, Any>()
            transactionMap["date"] = currentDate
            transactionMap["time"] = currentTime
            transactionMap["orderedItems"] = orders
            transactionMap["totalItem"] = totalItem
            transactionMap["totalPrice"] = totalPrice
            transactionMap["promoId"] = "promoId" // Replace this with your actual promo ID if available
            transactionMap["userId"] = "userId" // Replace this with the actual user ID

            // Get reference to Firebase database for transactions
            fbRef1 = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("transactions")
            val transactionKey = fbRef1.push().key

            // Push transaction data to Firebase database
            if (transactionKey != null) {
                fbRef1.child(transactionKey).setValue(transactionMap)
                    .addOnSuccessListener {
                        // Handle success
                        Log.d("Firebase", "Transaction added successfully")
                    }
                    .addOnFailureListener {
                        // Handle failure
                        Log.e("Firebase", "Failed to add transaction: $it")
                    }
            }
        } else {
            Log.d("MenuFragment", "No items to process/order")
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    interface OrderItemClickListener : MenuItemAdapter.OnItemChangedListener {
        fun onOrderItemsSelected(orderedMenu: List<MenuItem>)
    }

}