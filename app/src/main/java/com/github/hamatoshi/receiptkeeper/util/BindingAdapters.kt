package com.github.hamatoshi.receiptkeeper.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.hamatoshi.receiptkeeper.data.Receipt


@BindingAdapter("totalPrice")
fun TextView.setTotalPrice(item: Receipt?) {
    item?.let {
        text = item.total.toString()
    }
}
