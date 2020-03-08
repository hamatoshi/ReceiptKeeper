package com.github.hamatoshi.receiptkeeper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts_table")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L,

    @ColumnInfo(name = "receiptId")
    var receiptId: Long = 0L,

    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "shop")
    var shop: String = "",

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "item")
    var item: String = "",

    @ColumnInfo(name = "price")
    var price: Int = 0,

    @ColumnInfo(name = "tax")
    var tax: Float = 0F,

    @ColumnInfo(name = "tax_type")
    var taxType: Int = 0
)