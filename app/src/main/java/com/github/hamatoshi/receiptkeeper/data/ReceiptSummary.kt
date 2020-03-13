package com.github.hamatoshi.receiptkeeper.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.github.hamatoshi.receiptkeeper.util.totalPriceTaxOnTotal
import com.github.hamatoshi.receiptkeeper.util.totalPriceTaxOnEach
import com.github.hamatoshi.receiptkeeper.util.totalTaxPriceTaxOnTotal
import com.github.hamatoshi.receiptkeeper.util.totalTaxPriceTaxOnEach

enum class TaxOperationType(val value: Int) {
    Each(1),
    Total(2)
}

@Entity(tableName = "table_receipts")
@TypeConverters(TaxOperationTypeConverter::class)
data class ReceiptSummary(
    @PrimaryKey(autoGenerate = true)
    val receiptId: Long = 0L,

    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "shop")
    var shop: String = "",

    @ColumnInfo(name = "tax_operation")
    var taxOperation: TaxOperationType = TaxOperationType.Each,

    @ColumnInfo(name = "total")
    var total: Int = 0

) {
    @Ignore
    var contents: MutableList<ReceiptContent> = mutableListOf()
    @Ignore
    var isExpanded = false

    fun totalPrice(): Int {
        return when(taxOperation) {
            TaxOperationType.Each -> contents.totalPriceTaxOnEach()
            TaxOperationType.Total -> contents.totalPriceTaxOnTotal()
        }
    }

    fun totalTaxPrice(): Int {
        return when (taxOperation) {
            TaxOperationType.Each -> contents.totalTaxPriceTaxOnEach()
            TaxOperationType.Total -> contents.totalTaxPriceTaxOnTotal()
        }
    }
}

object TaxOperationTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toTaxOperationType(num: Int): TaxOperationType = TaxOperationType.values().first {it.value == num}

    @TypeConverter
    @JvmStatic
    fun toInt(taxOperation: TaxOperationType): Int = taxOperation.value
}

class ReceiptDiffCallback : DiffUtil.ItemCallback<ReceiptSummary>() {
    override fun areItemsTheSame(oldItem: ReceiptSummary, newItem: ReceiptSummary): Boolean = oldItem.receiptId == newItem.receiptId
    override fun areContentsTheSame(oldItem: ReceiptSummary, newItem: ReceiptSummary): Boolean = oldItem == newItem
}

object ReceiptStore {
    val allReceipts = listOf(
        ReceiptSummary(receiptId = 1, shop = "Seven Eleven", taxOperation = TaxOperationType.Each),
        ReceiptSummary(receiptId = 2, shop = "Olympic", taxOperation = TaxOperationType.Total),
        ReceiptSummary(receiptId = 3 ,shop = "Tokyu Store", taxOperation = TaxOperationType.Total),
        ReceiptSummary(receiptId = 4, shop = "Family Mart", taxOperation = TaxOperationType.Total)
    )
}