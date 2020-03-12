package com.github.hamatoshi.receiptkeeper.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReceiptSummaryDatabaseDao {
    @Insert
    fun insert(receiptSummary: ReceiptSummary)

    @Update
    fun update(receiptSummary: ReceiptSummary)

    @Query("SELECT * FROM table_receipts WHERE date BETWEEN :from AND :to ORDER BY date DESC, receiptId DESC")
    fun getReceiptsBetweenDate(from: Long, to: Long): LiveData<List<ReceiptSummary>>

    @Query("SELECT * FROM table_receipts WHERE receiptId =:key")
    fun getReceiptWithId(key: Long): ReceiptSummary?

    @Query("DELETE FROM table_receipts WHERE receiptId = :key")
    fun deleteReceiptWithId(key: Long)

    @Query("SELECT * FROM table_receipts ORDER BY date DESC, receiptId DESC")
    fun getAllReceipts(): LiveData<List<ReceiptSummary>>

    @Query("DELETE FROM table_receipts")
    fun clear()
}