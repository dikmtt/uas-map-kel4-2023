package com.example.uas_kelompok4.model

data class MenuItem(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var price: Int = 0,
    var category: String = "",
    var boughtValue: Int = 0
)
