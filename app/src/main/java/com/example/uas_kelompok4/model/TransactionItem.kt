package com.example.uas_kelompok4.model

data class TransactionItem(
    val id: String,
    var transactionId: String,
    var menuId: String,
    var qty: Int,
    var pricePerItem : Int
)