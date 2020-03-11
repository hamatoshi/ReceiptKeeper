package com.github.hamatoshi.receiptkeeper.data

import androidx.room.*

enum class TaxType(val value: Int) {
    NO_TAX(0),
    TAX_EXCLUDED(1),
    TAX_INCLUDED(2)
}

@Entity(tableName = "table_receipt_contents")
@TypeConverters(TaxTypeConverter::class)
data class ReceiptContent(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,

    @ColumnInfo(name = "receiptId")
    var receiptId: Long = 0L,

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "item")
    var item: String = "",

    @ColumnInfo(name = "net_price")
    var netPrice: Int = 0,

    @ColumnInfo(name = "tax")
    var tax: Int = 0,

    @ColumnInfo(name = "tax_type")
    var taxType: TaxType = TaxType.TAX_EXCLUDED,

    @ColumnInfo(name = "discount")
    var discount: Int = 0
) {
    @Ignore
    var price = netPrice - discount
    @Ignore
    var taxPrice = when (taxType) {
        TaxType.NO_TAX -> 0
        TaxType.TAX_EXCLUDED -> (price * tax) / 100
        TaxType.TAX_INCLUDED -> {
            if (tax == 0) 0
            else price - (price * 100 / (100 + tax) + 1)
        }
    }
    @Ignore
    var priceWithTax = when (taxType) {
        TaxType.NO_TAX -> price
        TaxType.TAX_EXCLUDED -> price + taxPrice
        TaxType.TAX_INCLUDED -> price
    }
    @Ignore
    var priceWithoutTax = when (taxType) {
        TaxType.NO_TAX -> price
        TaxType.TAX_EXCLUDED -> price
        TaxType.TAX_INCLUDED -> price - taxPrice
    }
}

object TaxTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toTaxType(num: Int): TaxType = TaxType.values().first { it.value == num }

    @TypeConverter
    @JvmStatic
    fun toInt(taxType: TaxType): Int = taxType.value
}

object ReceiptContentsStore {
    val receiptContentsSevenEleven = mutableListOf(
        ReceiptContent(1, 1, "food", "ramen", 550, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(2, 1, "drink", "latte", 168, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(3, 1, "fun", "cigarette", 450, 10, TaxType.TAX_INCLUDED)
    )
    val receiptContentsOlympic = mutableListOf(
        ReceiptContent(4, 2, "drink", "latte", 196, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(5, 2, "food", "snack", 167, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(6, 2, "food", "snack", 115, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(7, 2, "food", "bento", 498, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(8, 2, "drink", "brick juice", 174, 8, TaxType.TAX_EXCLUDED, 6)
    )
    val receiptContentsTokyuStore = mutableListOf(
        ReceiptContent(9, 3, "drink", "milk tea", 138, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(10, 3, "drink", "apple juice", 148, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(11, 3, "food", "instant noodle", 138, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(12, 3, "food", "pineapple", 198, 8, TaxType.TAX_EXCLUDED, 60),
        ReceiptContent(13, 3, "food", "bento", 580, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(14, 3, "food", "sweet bread", 98, 8, TaxType.TAX_EXCLUDED),
        ReceiptContent(15, 3, "food", "frozen food", 298, 8, TaxType.TAX_EXCLUDED)
    )
    val receiptContentsFamilyMart = mutableListOf(
        ReceiptContent(16, 4, "food", "snack bread", 128, 8, TaxType.TAX_INCLUDED),
        ReceiptContent(17, 4, "food", "rice balls", 298, 8, TaxType.TAX_INCLUDED),
        ReceiptContent(18, 4, "fun", "cigarette", 490, 10, TaxType.TAX_INCLUDED)
    )
}