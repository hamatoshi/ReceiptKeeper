package com.github.hamatoshi.receiptkeeper.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReceiptRepository (private val databaseDao: ReceiptDatabaseDao) {

    val allReceipts: LiveData<List<Receipt>> = databaseDao.getAllReceipts()

    fun getReceiptBetweenDate(from: Long, to: Long): LiveData<List<Receipt>> {
        return databaseDao.getReceiptsBetweenDate(from, to)
    }

    suspend fun getReceiptWithId(receiptId: Long): Receipt? {
        return withContext(Dispatchers.IO) {
            databaseDao.getReceiptWithId(receiptId)
        }
    }

    suspend fun insert(receipt: Receipt?) {
        withContext(Dispatchers.IO) {
            receipt?.let { databaseDao.insert(it) }
        }
    }

    suspend fun update(receipt: Receipt?) {
        withContext(Dispatchers.IO) {
            receipt?.let { databaseDao.update(it) }
        }
    }

    suspend fun deleteReceiptWithId(receiptId: Long) {
        withContext(Dispatchers.IO) {
            databaseDao.deleteReceiptWithId(receiptId)
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            databaseDao.clear()
        }
    }
}