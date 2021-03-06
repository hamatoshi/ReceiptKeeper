package com.github.hamatoshi.receiptkeeper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReceiptSummary::class, ReceiptContent::class], version = 1, exportSchema = false)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract val receiptSummaryDatabaseDao: ReceiptSummaryDatabaseDao
    abstract val receiptContentDatabaseDao: ReceiptContentDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: ReceiptDatabase? = null

        fun getInstance(context: Context): ReceiptDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReceiptDatabase::class.java,
                        "database_receipts"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}