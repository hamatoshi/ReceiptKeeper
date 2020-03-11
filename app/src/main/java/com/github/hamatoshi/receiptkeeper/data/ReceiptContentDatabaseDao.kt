package com.github.hamatoshi.receiptkeeper.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReceiptContentDatabaseDao {
    @Insert
    fun insert(receiptContent: ReceiptContent)

    @Update
    fun update(receiptContent: ReceiptContent)

    @Query("SELECT * FROM table_receipt_contents WHERE receiptId =:key ORDER BY uid DESC")
    fun getReceiptContentsWithId(key: Long): LiveData<List<ReceiptContent>>

    @Query("DELETE FROM table_receipt_contents")
    fun clear()
}