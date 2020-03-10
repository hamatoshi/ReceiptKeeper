package com.github.hamatoshi.receiptkeeper.util

import android.widget.ListView

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