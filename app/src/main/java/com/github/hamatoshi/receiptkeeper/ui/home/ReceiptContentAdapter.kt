package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.ReceiptContent
import com.github.hamatoshi.receiptkeeper.data.ReceiptContentDiffCallback
import com.github.hamatoshi.receiptkeeper.databinding.ListItemReceiptContentBinding

class ReceiptContentAdapter()
    : ListAdapter<ReceiptContent, RecyclerView.ViewHolder>(ReceiptContentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReceiptContentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReceiptContentViewHolder) {
            val item = getItem(position)
            holder.bind(item)
        }
    }

    class ReceiptContentViewHolder private constructor(private val binding: ListItemReceiptContentBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReceiptContent) {
            binding.receiptContent = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReceiptContentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemReceiptContentBinding.inflate(layoutInflater, parent, false)
                return ReceiptContentViewHolder(binding)
            }
        }
    }
}