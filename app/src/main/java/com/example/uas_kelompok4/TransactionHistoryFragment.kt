// FragmentTransactionHistory.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.uas_kelompok4.R
import com.example.uas_kelompok4.TransactionAdapter
import com.example.uas_kelompok4.model.TransactionItem

class FragmentTransactionHistory : Fragment() {
    private lateinit var transactionListView: ListView
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction_history, container, false)
        transactionListView = view.findViewById(R.id.transactionListView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your ListView and Adapter
        transactionAdapter = TransactionAdapter(requireContext(), getTransactionData())

        // Set the adapter to the ListView
        transactionListView.adapter = transactionAdapter
    }

    // This is a placeholder function to simulate transaction data
    private fun getTransactionData(): List<TransactionItem> {
        val transactionList = mutableListOf<TransactionItem>()

        // Add sample transactions (Replace this with your actual transaction data retrieval logic)
        transactionList.add(TransactionItem("Transaction 1", "Description 1", "1", 1000, 1000))
        transactionList.add(TransactionItem("Transaction 2", "Description 2", "1", 1000, 1000))
        transactionList.add(TransactionItem("Transaction 3", "Description 3", "1", 1000, 1000))

        return transactionList
    }
}
