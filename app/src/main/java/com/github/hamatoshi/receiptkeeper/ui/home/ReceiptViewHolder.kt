package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.databinding.ListItemReceiptBinding
import com.github.hamatoshi.receiptkeeper.util.setListViewHeightBasedOnChildren

class ReceiptViewHolder private constructor(private val binding: ListItemReceiptBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Receipt) {
        val listItems = item.contents.map { it.item }
        val adapter =
            ArrayAdapter(binding.listReceiptContents.context, android.R.layout.simple_list_item_1, listItems)

        binding.apply {
            receipt = item
            listReceiptContents.adapter = adapter
            listReceiptContents.setListViewHeightBasedOnChildren()
            listReceiptContents.visibility = if(item.isExpanded) { View.VISIBLE } else { View.GONE }
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