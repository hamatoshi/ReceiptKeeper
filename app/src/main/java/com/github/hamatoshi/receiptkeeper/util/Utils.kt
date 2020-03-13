package com.github.hamatoshi.receiptkeeper.util

import android.widget.ListView
import com.github.hamatoshi.receiptkeeper.data.ReceiptContent
import com.github.hamatoshi.receiptkeeper.data.TaxType

fun ListView.setListViewHeightBasedOnChildren() {
    adapter?.let {
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, this)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = layoutParams
        params.height = totalHeight + (dividerHeight * (adapter.count - 1))
        layoutParams = params
    }
}

fun MutableList<ReceiptContent>.totalTaxPriceTaxOnEach(): Int {
    return this.sumBy { it.taxPrice }
}

fun MutableList<ReceiptContent>.totalTaxPriceTaxOnTotal(): Int {
    val taxGroupLists = this.groupBy { it.tax }
    var result = 0
    for (list in taxGroupLists) {
        result += list.value.filter { it.taxType == TaxType.TAX_EXCLUDED }
            .sumBy { it.priceWithoutTax } *  list.key / 100
        result += list.value.filter { it.taxType == TaxType.TAX_INCLUDED }
            .sumBy { it.taxPrice }
    }
    return result
}

fun MutableList<ReceiptContent>.totalPriceTaxOnEach(): Int {
    return this.sumBy { it.priceWithTax }
}

fun MutableList<ReceiptContent>.totalPriceTaxOnTotal(): Int {
    val taxGroupLists = this.groupBy { it.tax }
    var result = 0
    for (list in taxGroupLists) {
        result += list.value.filter { it.taxType == TaxType.TAX_EXCLUDED }
            .sumBy { it.priceWithoutTax } * (100 + list.key) / 100
        result += list.value.filter { it.taxType == TaxType.TAX_INCLUDED }
            .sumBy { it.priceWithTax }
        result += list.value.filter { it.taxType == TaxType.NO_TAX }
            .sumBy { it.price }
    }
    return result
}