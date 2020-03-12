package com.github.hamatoshi.receiptkeeper.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReceiptSummaryRepository (private val summaryDatabaseDao: ReceiptSummaryDatabaseDao) {

    val allReceipts: LiveData<List<ReceiptSummary>> = summaryDatabaseDao.getAllReceipts()

    fun getReceiptBetweenDate(from: Long, to: Long): LiveData<List<ReceiptSummary>> {
        return summaryDatabaseDao.getReceiptsBetweenDate(from, to)
    }

    suspend fun getReceiptWithId(receiptId: Long): ReceiptSummary? {
        return withContext(Dispatchers.IO) {
            summaryDatabaseDao.getReceiptWithId(receiptId)
        }
    }

    suspend fun insert(receiptSummary: ReceiptSummary?) {
        withContext(Dispatchers.IO) {
            receiptSummary?.let { summaryDatabaseDao.insert(it) }
        }
    }

    suspend fun update(receiptSummary: ReceiptSummary?) {
        withContext(Dispatchers.IO) {
            receiptSummary?.let { summaryDatabaseDao.update(it) }
        }
    }

    suspend fun deleteReceiptWithId(receiptId: Long) {
        withContext(Dispatchers.IO) {
            summaryDatabaseDao.deleteReceiptWithId(receiptId)
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            summaryDatabaseDao.clear()
        }
    }
}