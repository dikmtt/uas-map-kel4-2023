package com.example.uas_kelompok4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.MenuItem

class OrderActivity : AppCompatActivity() {
    private val menuRV: RecyclerView by lazy {
        findViewById(R.id.menu_rv)
    }
    private val menuAdapter by lazy {
        MenuItemAdapter(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        menuRV.layoutManager = GridLayoutManager(this, 2)
        menuRV.adapter = menuAdapter

        menuAdapter.setData(listOf(
            MenuItem("1", "yee", "haw", 100, "yeet", 0),
            MenuItem("2", "tee", "hee", 200, "yeet", 0),
            MenuItem("3", "wee", "woo", 300, "yeet", 0)
        ))
    }
}