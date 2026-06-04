package com.anushka.fintrack.repository

import androidx.lifecycle.LiveData
import com.anushka.fintrack.data.AppDatabase
import com.anushka.fintrack.data.Transaction
import com.anushka.fintrack.data.TransactionDao

class TransactionRepository(private val dao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = dao.getAllTransactions()
    val totalIncome: LiveData<Double> = dao.getTotalIncome()
    val totalExpense: LiveData<Double> = dao.getTotalExpense()

    suspend fun insert(transaction: Transaction) {
        dao.insert(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        dao.delete(transaction)
    }
}