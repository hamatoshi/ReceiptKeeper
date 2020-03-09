package com.github.hamatoshi.receiptkeeper.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaxOperationType { Each, Total }

@Entity(tableName = "receipts_table")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,

    @ColumnInfo(name = "receiptId")
    var receiptId: Long = 0L,

    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "shop")
    var shop: String = "",

    @ColumnInfo(name = "tax_operation")
    var taxOperation: TaxOperationType = TaxOperationType.Each,

    @ColumnInfo(name = "total")
    var total: Int = 0

) {
    var contents: MutableList<ReceiptContent> = mutableListOf()
}

object ReceiptUtil {
    fun total(receipt: Receipt): Int {
        return when (receipt.taxOperation) {
            TaxOperationType.Each -> receipt.contents.sumBy { it.priceWithTax }
            TaxOperationType.Total -> {
                val taxGroupLists = receipt.contents.groupBy { it.tax }
                var result = 0
                for (list in taxGroupLists) {
                    result += list.value.filter { it.taxType == TaxType.TAX_EXCLUDED }
                        .sumBy { it.priceWithoutTax } * (100 + list.key) / 100
                    result += list.value.filter { it.taxType == TaxType.TAX_INCLUDED }
                        .sumBy { it.priceWithTax }
                    result += list.value.filter { it.taxType == TaxType.NO_TAX }
                        .sumBy { it.price }
                }
                result
            }
        }
    }
}

class ReceiptDiffCallback : DiffUtil.ItemCallback<Receipt>() {
    override fun areItemsTheSame(oldItem: Receipt, newItem: Receipt): Boolean = oldItem.uid == newItem.uid
    override fun areContentsTheSame(oldItem: Receipt, newItem: Receipt): Boolean = oldItem == newItem
}

object ReceiptStore {
    val allReceipts = listOf(
        Receipt(uid = 1, receiptId = 1, shop = "Seven Eleven", taxOperation = TaxOperationType.Each),
        Receipt(uid = 2, receiptId = 2, shop = "Olympic", taxOperation = TaxOperationType.Total),
        Receipt(uid = 3, receiptId = 3 ,shop = "Tokyu Store", taxOperation = TaxOperationType.Total),
        Receipt(uid = 4, receiptId = 4, shop = "Family Mart", taxOperation = TaxOperationType.Total)
    )
}