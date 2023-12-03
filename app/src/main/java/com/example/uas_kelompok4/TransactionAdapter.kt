package com.example.uas_kelompok4

// TransactionAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.uas_kelompok4.model.TransactionItem
class TransactionAdapter(
    private val context: Context,
    private val transactionList: List<TransactionItem>
) : BaseAdapter() {

    override fun getCount(): Int {
        return transactionList.size
    }

    override fun getItem(position: Int): Any {
        return transactionList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        val holder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
            holder = ViewHolder()
            holder.transactionName = itemView.findViewById(R.id.transactionNameTextView)
            holder.description = itemView.findViewById(R.id.descriptionTextView)
            holder.date = itemView.findViewById(R.id.dateTextView)
            itemView.tag = holder
        } else {
            holder = itemView.tag as ViewHolder
        }

        val transaction = getItem(position) as TransactionItem
        holder.transactionName.text = transaction.id
        holder.description.text = transaction.transactionId
        holder.date.text = transaction.menuId

        return itemView!!
    }

    private class ViewHolder {
        lateinit var transactionName: TextView
        lateinit var description: TextView
        lateinit var date: TextView
    }
}
