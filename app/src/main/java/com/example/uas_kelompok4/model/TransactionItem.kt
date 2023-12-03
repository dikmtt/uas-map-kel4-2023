package com.example.uas_kelompok4.model

//Data class ini untuk item menu yang ada di setiap transaksi
//Misalnya, sebuah transaksi berID 1 punya 2 transaction item
//1 Nasi ayam dan 2 Es teh manis
//Maka, ada 2 Transaction Item untuk transaction ID 1, masing-masing item menu punya baris sendiri
//Qty diambil dari boughtValue saat order makanan, dan akan direset kembali ke 0 setelah transaksi masuk database
//PricePerItem adalah harga masing-masing (tanpa dikalikan qty)
data class TransactionItem(
    var id: String,
    var transactionId: String,
    var menuId: String,
    var qty: Int,
    var pricePerItem : Int
)