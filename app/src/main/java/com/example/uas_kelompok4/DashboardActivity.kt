package com.example.uas_kelompok4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.model.User


//CurrUser is based on the user currently logging in
//currUser role jangan di hardcode
lateinit var currUser: User
//User("admin", "admin@resto.com", "admin", "Admin")
@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val ft = supportFragmentManager.beginTransaction()
        currUser = intent.extras?.getParcelable<User>("USER")!!
        Log.d("currUser", currUser.toString())
        if(currUser.role == "Member") {
            val dashb = CustomerDashboardFragment()
            val bund = Bundle()
            bund.putParcelable("user", currUser)
            dashb.arguments = bund
            ft.replace(R.id.dashboard_segment, dashb)
            ft.commit()
        }
        else {
            val dashb = DashboardFragment()
            val bund = Bundle()
            bund.putParcelable("user", currUser)
            dashb.arguments = bund
            ft.replace(R.id.dashboard_segment, dashb)
            ft.commit()
        }
    }
}