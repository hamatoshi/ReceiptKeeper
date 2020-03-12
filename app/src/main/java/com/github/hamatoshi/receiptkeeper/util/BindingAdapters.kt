package com.github.hamatoshi.receiptkeeper.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.hamatoshi.receiptkeeper.R
import com.github.hamatoshi.receiptkeeper.data.ReceiptContent
import com.github.hamatoshi.receiptkeeper.data.TaxType
import java.text.DecimalFormat

private const val WESTERN_PATTERN = "#,##0.00"
private const val JAPANESE_PATTERN = "#,###"

@BindingAdapter("price")
fun TextView.setPrice(price: Int?) {
    price?.let {
        val formatter = DecimalFormat(WESTERN_PATTERN)
        val formattedNumber = formatter.format(price.toDouble() / 100)
        text = resources.getString(R.string.string_price_dollar, formattedNumber)
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