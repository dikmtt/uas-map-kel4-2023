package com.example.uas_kelompok4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_kelompok4.model.User

//Sample
public var currUser: User = User("1", "Admin", "admin@resto.com", "ooofff", "Admin")
//User("admin", "admin@resto.com", "admin", "Admin")

//CurrUser is based on the user currently logging in
@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val intent = intent
        currUser = intent.getParcelableExtra<User>("USER")!!
        val ft = supportFragmentManager.beginTransaction()
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