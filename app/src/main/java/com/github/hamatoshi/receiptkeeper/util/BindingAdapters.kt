package com.github.hamatoshi.receiptkeeper.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.data.ReceiptContent
import com.github.hamatoshi.receiptkeeper.data.TaxType


@BindingAdapter("totalPrice")
fun TextView.setTotalPrice(item: Receipt?) {
    item?.let {
        text = item.total.toString()
    }
}

@BindingAdapter("price")
fun TextView.setPrice(item: ReceiptContent?) {
    item?.let {
        text = item.price.toString()
    }
}

@BindingAdapter("taxType")
fun TextView.setTaxType(item: ReceiptContent?) {
    item?.let {
        text = when (item.taxType) {
            TaxType.NO_TAX -> "No tax"
            TaxType.TAX_INCLUDED -> "Tax included"
            TaxType.TAX_EXCLUDED -> "Tax excluded"
        }
    }

}