package com.example.uas_kelompok4.model

data class User(
    val id: String,
    var name: String,
    var email: String,
    var password: String,
    var role: String
)