package com.example.uas_kelompok4

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private val currUser: User = User("Ken", "ken@resto.com", "ooofff", "Staff")
    //User("admin", "admin@resto.com", "admin", "Admin")
    val showUsers = requireView().findViewById<View>(R.id.show_users)
    val addMenus = requireView().findViewById<View>(R.id.add_menu)
    val addPromos = requireView().findViewById<View>(R.id.add_promo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(currUser.role == "Admin") {
            addMenus.visibility = View.VISIBLE
            addPromos.visibility = View.VISIBLE
        } else {
            addMenus.visibility = View.GONE
            addPromos.visibility = View.GONE
        }
        showUsers.setOnClickListener {
            val i: Intent = Intent(activity, UserList::class.java)
            startActivity(i)
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
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