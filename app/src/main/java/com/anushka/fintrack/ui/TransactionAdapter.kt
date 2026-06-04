package com.anushka.fintrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anushka.fintrack.R
import com.anushka.fintrack.data.Transaction

class TransactionAdapter(
    private var transactions: List<Transaction>,
    private val onDeleteClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val tvAmount: TextView = itemView.findViewById(R.id.tvItemAmount)
        val tvCategory: TextView = itemView.findViewById(R.id.tvItemCategory)
        val tvDate: TextView = itemView.findViewById(R.id.tvItemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.tvTitle.text = transaction.title
        holder.tvCategory.text = transaction.category

        // Format date
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
        holder.tvDate.text = sdf.format(java.util.Date(transaction.date))

        // Colour amount green for income, red for expense
        if (transaction.type == "INCOME") {
            holder.tvAmount.text = "+ ₹%.2f".format(transaction.amount)
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        } else {
            holder.tvAmount.text = "- ₹%.2f".format(transaction.amount)
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#F44336"))
        }

        // Long press to delete
        holder.itemView.setOnLongClickListener {
            onDeleteClick(transaction)
            true
        }
    }

    override fun getItemCount() = transactions.size

    fun updateData(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}