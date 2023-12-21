// FragmentTransactionHistory.kt
package com.example.uas_kelompok4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.Transaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransactionHistoryFragment : Fragment() {
    private lateinit var transactionAdapter: RecyclerView.Adapter<*>
    private lateinit var layManager: RecyclerView.LayoutManager

    private lateinit var fbRef: DatabaseReference
    private lateinit var listOfTransactions: ArrayList<Transaction>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction_history, container, false)
        //fbRef = FirebaseDatabase.getInstance().getReference("transactions")
        listOfTransactions = arrayListOf()
        getTransactionData()
        //transactionListView = view.findViewById(R.id.transactionListView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layManager = LinearLayoutManager(activity)
        val transactionRV = view.findViewById<RecyclerView>(R.id.rv_transaction)
        transactionRV.apply {
            transactionAdapter = TransactionAdapter(listOfTransactions)
            layoutManager = layManager
            setHasFixedSize(true)
            adapter = transactionAdapter

            (transactionAdapter as TransactionAdapter).onClick = {
                val stFrag: Fragment = SingleTransactionFragment()
                val stBundle = Bundle()
                stBundle.putParcelable("tran", it)
                stFrag.arguments = stBundle
                val ft = parentFragmentManager.beginTransaction()
                ft.replace(R.id.transactionPart, stFrag)
                ft.commit()
            }
        }

        // Initialize your ListView and Adapter
        //transactionAdapter = TransactionAdapter(requireContext(), getTransactionData())

        // Set the adapter to the ListView
        //transactionListView.adapter = transactionAdapter
    }

    // This is a placeholder function to simulate transaction data
    private fun getTransactionData() {
        fbRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("transactions")
        fbRef.addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfTransactions.clear()
                if (snapshot.exists()) {
                    for (tSnap in snapshot.children) {
                        val tranIt = tSnap.getValue(Transaction::class.java)
                        listOfTransactions.add(tranIt!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Transaction DB Error", Toast.LENGTH_LONG).show()
            }
        })
    }
}
