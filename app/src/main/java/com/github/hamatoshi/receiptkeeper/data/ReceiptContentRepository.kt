package com.github.hamatoshi.receiptkeeper.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReceiptContentRepository (private val databaseDao: ReceiptContentDatabaseDao) {

    suspend fun getReceiptContentsWithId(receiptId: Long): LiveData<List<ReceiptContent>>? {
        return withContext(Dispatchers.IO) {
            databaseDao.getReceiptContentsWithId(receiptId)
        }
    }

    suspend fun insert(receiptContent: ReceiptContent?) {
        withContext(Dispatchers.IO) {
            receiptContent?.let {
                databaseDao.insert(it)
            }
        }
    }

    suspend fun update(receiptContent: ReceiptContent?) {
        withContext(Dispatchers.IO) {
            receiptContent?.let {
                databaseDao.update(it)
            }
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            databaseDao.clear()
        }
    }
}