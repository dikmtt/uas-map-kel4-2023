package com.example.uas_kelompok4.model

data class MenuItem(
    val id: String,
    var name: String,
    var imageUrl: String,
    var price: Int,
    var category: String,
    var boughtValue: Int
)