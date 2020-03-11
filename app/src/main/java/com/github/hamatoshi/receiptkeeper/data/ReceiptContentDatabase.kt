package com.github.hamatoshi.receiptkeeper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReceiptContent::class], version = 1, exportSchema = false)
abstract class ReceiptContentDatabase : RoomDatabase() {
    abstract val receiptContentDatabaseDao: ReceiptContentDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: ReceiptContentDatabase? = null

        fun getInstance(context: Context): ReceiptContentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReceiptContentDatabase::class.java,
                        "database_receipt_contents"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}