package com.example.uas_kelompok4

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_kelompok4.model.TransactionItem

class TransactionViewModel : ViewModel() {
    val transactions: List<TransactionItem> = listOf(
        TransactionItem("1", "Transaction 1", "1", 1000, 1000),
    )
}

@Composable
fun TransactionViewModelFactory(): TransactionViewModel {
    return remember { TransactionViewModel() }
}
