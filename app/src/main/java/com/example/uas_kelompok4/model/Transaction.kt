package com.example.uas_kelompok4.model

import android.os.Parcel
import android.os.Parcelable

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

): Parcelable {

    constructor() : this("", "", listOf(), 0, "", 0, "")
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(MenuItem)!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeTypedList(transactionItems)
        parcel.writeInt(totalItems)
        parcel.writeString(promoId)
        parcel.writeInt(totalPrice)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}