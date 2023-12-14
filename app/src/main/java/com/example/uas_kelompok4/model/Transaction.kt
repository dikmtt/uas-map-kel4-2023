package com.example.uas_kelompok4.model

//Data class ini untuk transaction utama, yaitu informasi tentang transaksi
//Date dan Time (diambil dari Calendar.GetInstance) dan ID User yang melakukan transaksi
//Total diambil dari hasil penjumlahan semua transaction item dengan ID transaction yang bersagkutan
//Sebuah transaksi bisa memiliki promo, final price transaksi adalah harga setelah promo
//Tanpa promo, final price sama dengan harga di kolom total
data class Transaction(
    var date: String,
    var time: String,
    var transactionItems: List<MenuItem>,
    var totalItems: Int,
    var promoId: String,
    var totalPrice: Int,
    var userId: String

)