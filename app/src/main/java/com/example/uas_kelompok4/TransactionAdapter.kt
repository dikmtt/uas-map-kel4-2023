package com.example.uas_kelompok4

// TransactionAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_kelompok4.model.Transaction
class TransactionAdapter(
     val transactionList: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {
    var onClick : ((Transaction) -> Unit)? = null

    class TransactionHolder(view: View): RecyclerView.ViewHolder(view) {
        val date = view.findViewById<TextView>(R.id.transactionDateTextView)
        val time = view.findViewById<TextView>(R.id.transactionTimeTextView)
        val total = view.findViewById<TextView>(R.id.transactionTotalTextView)
        val trUser = view.findViewById<TextView>(R.id.transactionUserTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transactionAt = transactionList[position]
        holder.date.text = transactionAt.date
        holder.time.text = transactionAt.time
        holder.total.text = transactionAt.totalPrice.toString()
        holder.trUser.text = transactionAt.userId

        holder.itemView.setOnClickListener{
            onClick?.invoke(transactionAt)
        }
    }
}
