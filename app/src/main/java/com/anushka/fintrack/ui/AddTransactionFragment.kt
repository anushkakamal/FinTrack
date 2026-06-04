package com.anushka.fintrack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anushka.fintrack.R
import com.anushka.fintrack.data.Transaction
import com.anushka.fintrack.viewmodel.TransactionViewModel
import com.google.android.material.textfield.TextInputEditText

class AddTransactionFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val title = view.findViewById<TextInputEditText>(R.id.etTitle).text.toString().trim()
            val amountStr = view.findViewById<TextInputEditText>(R.id.etAmount).text.toString().trim()
            val category = view.findViewById<TextInputEditText>(R.id.etCategory).text.toString().trim()
            val rgType = view.findViewById<RadioGroup>(R.id.rgType)
            val type = if (rgType.checkedRadioButtonId == R.id.rbIncome) "INCOME" else "EXPENSE"

            // Validation
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (amountStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter an amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (category.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaction = Transaction(
                title = title,
                amount = amount,
                category = category,
                type = type,
                date = System.currentTimeMillis()
            )

            viewModel.insert(transaction)
            Toast.makeText(requireContext(), "Transaction saved!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}