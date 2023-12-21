package com.example.uas_kelompok4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uas_kelompok4.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var currUser: User
    lateinit var showUsers: View
    lateinit var addMenus: View
    lateinit var addPromos: View
    lateinit var updateMenus: View
    lateinit var showTransaction: View


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
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val bund = arguments
        currUser = bund?.getParcelable<User>("user")!!
        Log.d("currUser", currUser.toString())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUsers = requireView().findViewById<View>(R.id.show_users)
        addMenus = requireView().findViewById<View>(R.id.add_menu)
        addPromos = requireView().findViewById<View>(R.id.add_promo)
        updateMenus = requireView().findViewById<View>(R.id.update_menu)
        showTransaction = requireView().findViewById<View>(R.id.show_transactions)

        if(currUser.role == "Admin") {
            addMenus.visibility = View.VISIBLE
            addPromos.visibility = View.VISIBLE
            updateMenus.visibility = View.VISIBLE
        } else if(currUser.role == "Staff") {
            addMenus.visibility = View.GONE
            addPromos.visibility = View.GONE
            updateMenus.visibility = View.GONE
        }
        showUsers.setOnClickListener {
            val i: Intent = Intent(activity, UserList::class.java)
            startActivity(i)
        }
        addMenus.setOnClickListener {
            val i: Intent = Intent(activity, AddMenuActivity::class.java)
            startActivity(i)
        }
        addPromos.setOnClickListener {
            val i: Intent = Intent(activity, AddPromoActivity::class.java)
            startActivity(i)
        }
        updateMenus.setOnClickListener {
            val i: Intent = Intent(activity, UpdateMenusActivity::class.java)
            startActivity(i)
        }

        showTransaction.setOnClickListener{
            val i: Intent = Intent(activity, TransactionHistActivity::class.java)
            startActivity(i)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}