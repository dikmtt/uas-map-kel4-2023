package com.example.uas_kelompok4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.MenuItem
import com.example.uas_kelompok4.model.Promo
import com.example.uas_kelompok4.model.User
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
private const val ORDER_LIST = "order_list"
private const val TOTALITEMS = "total_items"
private const val TOTALPRICE = "total_price"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmOrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var orders = ArrayList<MenuItem>()
    private var orderedMenu = mutableListOf<MenuItem>()
    private lateinit var orderAdapter: RecyclerView.Adapter<*>
    private lateinit var layManager: RecyclerView.LayoutManager

    private lateinit var fbRef: DatabaseReference
    private lateinit var fbRef1: DatabaseReference
    private lateinit var listOfPromos: ArrayList<Promo>
    private lateinit var promoNames: ArrayList<String>

    private var promoPos : Int = 0
    private var totalItems : Int = 0
    private var totalPrice : Int = 0
    private var totalDiscPrice : Int = 0

    private var totalMenu : Int = 0
    private var totalPrices : Int = 0
    private var selectedPromoPosition: Int = 0

    private var orderProcessingListener: OrderProcessingListener? = null

    private var currUser: User? = null

    // Function to set the listener
    fun setOrderProcessingListener(listener: OrderProcessingListener) {
        orderProcessingListener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            totalItems = it.getInt(TOTALITEMS, 0) // Use default value if not present
            totalPrice = it.getInt(TOTALPRICE, 0) // Use default value if not present
            orders = it.getParcelableArrayList(ORDER_LIST) ?: ArrayList()
            currUser= it.getParcelable("currUser")
            Log.d("currUser", currUser.toString())
        }
        orderedMenu = orders
        totalMenu = totalItems
        totalPrices = totalPrice
        totalDiscPrice = totalPrices
        val oa = OrderActivity()
        if(oa.currUser != null) {
            currUser = oa.currUser
        }
        Log.d("currUser", "$currUser")
    }

    override fun onStart() {
        super.onStart()
        updateTotalValues()
    }
    fun storeValue(){
        orderedMenu = orders
        totalMenu = totalItems
        totalPrices = totalPrice
    }
    fun updateTotalValues() {
        var totalItem = 0
        var totalPrice = 0

        for (selectedItem in orders) {
            totalPrice += selectedItem.price * selectedItem.boughtValue
            totalItem += selectedItem.boughtValue
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TOTALITEMS, totalItems)
        outState.putInt(TOTALPRICE, totalPrice)
        outState.putParcelableArrayList(ORDER_LIST, orders)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirm_order, container, false)
        fbRef = FirebaseDatabase.getInstance().getReference("promo")
        listOfPromos = arrayListOf()
        promoNames = arrayListOf()
        if(currUser != null) {
            getPromos()
        }
        else {
            listOfPromos.add(Promo("None", "None", 0.0, 0))
            promoNames.add("None")
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layManager = LinearLayoutManager(activity)
        val orderList = view.findViewById<RecyclerView>(R.id.order_table)
        val cTotal = view.findViewById<TextView>(R.id.confirmTotal)
        val dcTotal = view.findViewById<TextView>(R.id.confirmDiscTotal)
        val disc = view.findViewById<TextView>(R.id.discAmount)
        //val orders = mutableListOf<MenuItem>()
        orderList.apply {
            orderAdapter = OrderItemAdapter(orders)
            layoutManager = layManager
            setHasFixedSize(true)
            adapter = orderAdapter
        }

        var promoList = view.findViewById<Spinner>(R.id.promoList)
        val promoAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, promoNames)
        promoList.adapter = promoAdapter

        promoList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position >= 0 && position < promoNames.size) {

                    selectedPromoPosition = position
                    val selectedPromo = promoNames[position]

                    if(promoNames[position] == "None"){}
                    else{
                        val toastMessage = "$selectedPromo has been selected"
                        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
                    }
                    Log.d("totalItems", totalItems.toString())
                    Log.d("listOfPromos[position].minPurchase",
                        listOfPromos[position].minPurchase.toString()
                    )

                    if (listOfPromos[position].name != "None"||listOfPromos[position].discAmount != null || listOfPromos[position].discAmount != 0.0) {
                        // Calculate the discounted price
                        if(totalItems.toString().toInt() < listOfPromos[position].minPurchase.toString().toInt() || totalItems.toString().toInt() < listOfPromos[position].minPurchase.toString().toInt()&& listOfPromos[position].name != "None"){
                            dcTotal.text = "Discounted Total:\t\t\t\t\t\t\t $totalPrices" // Update the text here as needed
                            disc.text = "Discount Amount: \t\t\t\t\t\t\t $0%"
                            val toastMessage = "Perlu membeli minimal " + listOfPromos[position].minPurchase.toString().toInt()
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val discountAmount =
                                totalPrices * (listOfPromos[position].discAmount!! / 100.0)
                            val discString = listOfPromos[position].discAmount!!
                            totalDiscPrice = totalPrices - discountAmount.toInt()

                            // Update the TextView with the discounted total price
                            dcTotal.text =
                                "Discounted Total:\t\t\t\t\t\t\t $totalDiscPrice" // Update the text here as needed
                            disc.text = "Discount Amount: \t\t\t\t\t\t\t $discString%"
                        }
                    } else {
                        // Handle the case when the discAmount is null
                        dcTotal.text = "Discounted Total:\t\t\t\t\t\t\t $totalPrices" // Update the text here as needed
                        disc.text = "Discount Amount: \t\t\t\t\t\t\t $0%"
                    }
                }
                else {
                    Log.d("selectedPromo", "No selected promo or invalid position")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected (if needed)
            }
        }


        val btn = view.findViewById<Button>(R.id.order_to_review_btn)
        btn.setOnClickListener {
            processOrder(selectedPromoPosition)

        }
//        btn.setOnClickListener {
//            AlertDialog.Builder(requireContext())
//                .setTitle("Confirmation")
//                .setMessage("Are you sure with your order? Make order or cancel.")
//                .setPositiveButton("Make Order") { _, _ ->
//                    // Process the order when the user clicks "Make Order"
//                    processOrder(selectedPromoPosition)
//                }
//                .setNegativeButton("No") { dialog, _ ->
//                    // Dismiss the dialog when the user clicks "No"
//                    dialog.dismiss()
//                }
//                .show()
//        }

        cTotal.text = "Total:\t\t\t\t\t\t\t $totalPrice"
        dcTotal.text ="Discounted Total:\t\t\t\t\t\t\t $totalDiscPrice"
        disc.text = "Discount Amount: \t\t\t\t\t\t\t $0%"

    }

    private fun getPromos() {
        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("promos")
        fbRef.addValueEventListener(object:
        ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfPromos.clear()
                promoNames.clear()
                listOfPromos.add(Promo("None", "None", 0.0, 0))
                promoNames.add("None")
                if(snapshot.exists()) {
                    for(promoSnap in snapshot.children) {
                        val promoIt = promoSnap.getValue(Promo::class.java)
                        listOfPromos.add(promoIt!!)
                        promoIt!!.name?.let { promoNames.add(it) }
                    }
                }
                updatePromoSpinner()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun updatePromoSpinner() {
        val promoAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, promoNames)
        val promoList = view?.findViewById<Spinner>(R.id.promoList)
        promoList?.adapter = promoAdapter
        promoAdapter.notifyDataSetChanged()
    }

    fun processOrder(position : Int) {
        val selectedPromo = listOfPromos[position]
        Log.d("selectedPromo" , "$selectedPromo")
        var totalItem = 0
        var totalPrice = 0
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        for (selectedItem in orders) {
            totalPrice += selectedItem.price * selectedItem.boughtValue
            totalItem += selectedItem.boughtValue
        }
        if(totalPrice == 0){
            showNoItemsDialog()
        }else{
            if (orders.isNotEmpty()) {
                if (selectedPromo != null && selectedPromo.minPurchase!! <= totalItem) {
                    // Calculate the discount based on the discount amount of the selected promo
                    val discountAmount = if (selectedPromo != null && selectedPromo.discAmount != null) {
                        totalPrice * (selectedPromo.discAmount!! / 100.0)
                    } else {
                        0.0 // Or any default value if discAmount is null or selectedPromo is null
                    }

                    totalPrice -= discountAmount.toInt()
                }
                val transactionMap = HashMap<String, Any>()
                transactionMap["date"] = currentDate
                transactionMap["time"] = currentTime
                transactionMap["orderedItems"] = orders
                transactionMap["totalItem"] = totalItem
                transactionMap["totalPrice"] = totalPrice
                transactionMap["promoId"] = listOfPromos[selectedPromoPosition].id!! // Replace this with your actual promo ID if available
                if(currUser != null) {
                    transactionMap["userId"] = currUser!!.email // Replace this with the actual user ID
                }
                else {
                    transactionMap["userId"] = "Guest"
                }
                // Get reference to Firebase database for transactions
                fbRef1 = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("transactions")
                val transactionKey = fbRef1.push().key

                // Push transaction data to Firebase database
                if (transactionKey != null) {
                    fbRef1.child(transactionKey).setValue(transactionMap)
                        .addOnSuccessListener {
                            // Handle success
                            Log.d("Firebase", "Transaction added successfully")
                            showSuccessDialog()
                        }
                        .addOnFailureListener {
                            // Handle failure
                            Log.e("Firebase", "Failed to add transaction: $it")
                        }
                }
            } else {
                Log.d("MenuFragment", "No items to process/order")
                showNoItemsDialog()
            }
        }
    }
    interface OrderProcessingListener {
        fun onOrderProcessed(orders: ArrayList<MenuItem>, totalItems: Int, totalPrice: Int)
    }


    // Inside your Fragment or Activity class

    private fun showNoItemsDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("No Items to Order")
                .setMessage("There are no items in your order list.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun showSuccessDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Success")
                .setMessage("Transaction added successfully!")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .create()
                .show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(totalItems: Int, totalPrice: Int, orderList: ArrayList<MenuItem>, currUser: User?) =
            ConfirmOrderFragment().apply {
                arguments = Bundle().apply {
                    putInt(TOTALITEMS, totalItems)
                    putInt(TOTALPRICE, totalPrice)
                    putParcelableArrayList(ORDER_LIST, orderList)
                    putParcelable("currUser", currUser) // Ensure to pass currUser here
                }
            }
    }
}