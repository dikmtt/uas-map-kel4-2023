package com.example.uas_kelompok4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

public var currUser: User = User("Ken", "ken@resto.com", "ooofff", "Staff")
//User("admin", "admin@resto.com", "admin", "Admin")
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val ft = supportFragmentManager.beginTransaction()
        if(currUser.role == "Member") {
            val dashb = CustomerDashboardFragment()
            ft.replace(R.id.dashboard_segment, dashb)
            ft.commit()
        }
        else {
            val dashb = DashboardFragment()
            ft.replace(R.id.dashboard_segment, dashb)
            ft.commit()
        }
    }
}