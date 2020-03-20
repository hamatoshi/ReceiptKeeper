package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.ReceiptSummary
import com.github.hamatoshi.receiptkeeper.databinding.ListItemReceiptBinding

class ReceiptViewHolder private constructor(private val binding: ListItemReceiptBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ReceiptSummary, clickListener: ReceiptEditClickListener) {
        val adapter = ReceiptContentAdapter()
        adapter.submitList(item.contents)
        binding.apply {
            receiptSummary = item
            listReceiptContents.adapter = adapter
            editClickListener = clickListener
            //listReceiptContents.setListViewHeightBasedOnChildren()
            containerReceiptContents.visibility = if(item.isExpanded) { View.VISIBLE } else { View.GONE }
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