package com.anushka.fintrack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anushka.fintrack.R
import com.anushka.fintrack.viewmodel.TransactionViewModel

class DashboardFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = TransactionAdapter(emptyList()) { transaction ->
            viewModel.delete(transaction)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvRecentTransactions)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observe transactions
        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            adapter.updateData(transactions)
        }

        // Observe balance
        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            val safeIncome = income ?: 0.0
            view.findViewById<TextView>(R.id.tvIncome).text = "₹%.2f".format(safeIncome)
            updateBalance(view)
        }

        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            val safeExpense = expense ?: 0.0
            view.findViewById<TextView>(R.id.tvExpense).text = "₹%.2f".format(safeExpense)
            updateBalance(view)
        }

        // Button navigation
        view.findViewById<Button>(R.id.btnAddTransaction).setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addTransaction)
        }

        view.findViewById<Button>(R.id.btnHistory).setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_history)
        }
    }

    private fun updateBalance(view: View) {
        val income = viewModel.totalIncome.value ?: 0.0
        val expense = viewModel.totalExpense.value ?: 0.0
        val balance = income - expense
        view.findViewById<TextView>(R.id.tvBalance).text = "₹%.2f".format(balance)
    }
}