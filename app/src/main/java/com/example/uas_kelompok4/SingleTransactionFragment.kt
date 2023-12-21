package com.example.uas_kelompok4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.MenuItem
import com.example.uas_kelompok4.model.Transaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val backButton: View? =
        view?.findViewById(R.id.backButton)
    private var tran: Transaction? = Transaction("aa", "bb", listOf(), 0, "y", 0, "e")
    private lateinit var menuList: List<MenuItem>

    private val tranUser = view?.findViewById<TextView>(R.id.st_user)
    private val tranDateTime = view?.findViewById<TextView>(R.id.st_datetime)
    private val tranTotPrice = view?.findViewById<TextView>(R.id.st_totPrice)
    private val tranTotItems = view?.findViewById<TextView>(R.id.st_totItems)
    private val tranRV = view?.findViewById<RecyclerView>(R.id.st_orders)

    private lateinit var orderAdapter: RecyclerView.Adapter<*>
    private lateinit var layManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_transaction, container, false)
        val bundle = arguments
        tran = bundle?.getParcelable<Transaction>("tran")
        menuList = tran!!.transactionItems
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton?.setOnClickListener {
            val tranFrag = TransactionHistoryFragment()
            val ft = parentFragmentManager?.beginTransaction()
            ft?.replace(R.id.transactionPart, tranFrag)
            ft?.commit()
        }
        setData(tran!!)
    }

    fun setData(tran: Transaction) {
        tranUser?.text = tran.userId
        tranDateTime?.text = "Date: " + tran.date + " / Time: " + tran.time
        tranTotItems?.text = "Total Items: \t\t" + tran.totalItems.toString()
        tranTotPrice?.text = "Total Price: \t\t" + tran.totalPrice.toString()
        tranRV?.apply {
            orderAdapter = OrderItemAdapter(menuList)
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = orderAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleTransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}