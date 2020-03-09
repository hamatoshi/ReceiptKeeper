package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.databinding.ListItemReceiptBinding

class ReceiptViewHolder private constructor(private val binding: ListItemReceiptBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Receipt) {
        val listItems = item.contents.map { it.item }
        val adapter = ArrayAdapter(binding.listReceiptContents.context, android.R.layout.simple_list_item_1, listItems)
        binding.apply {
            receipt = item
            listReceiptContents.adapter = adapter
            listReceiptContents.setListViewHeightBasedOnChildren()
            executePendingBindings()
        }
    }
    companion object {
        fun from(parent: ViewGroup): ReceiptViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemReceiptBinding.inflate(layoutInflater, parent, false)
            return ReceiptViewHolder(binding)
        }
    }
}

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