package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.databinding.ListItemReceiptBinding

class ReceiptViewHolder private constructor(private val binding: ListItemReceiptBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Receipt) {
        val adapter = ReceiptContentAdapter()
        adapter.submitList(item.contents)
        binding.apply {
            receipt = item
            listReceiptContents.adapter = adapter
            //listReceiptContents.setListViewHeightBasedOnChildren()
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