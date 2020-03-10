package com.github.hamatoshi.receiptkeeper.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.data.ReceiptDiffCallback

class ReceiptAdapter(): ListAdapter<Receipt, RecyclerView.ViewHolder>(ReceiptDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReceiptViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReceiptViewHolder) {
            val item = getItem(position)
            holder.bind(item)
            holder.itemView.setOnClickListener {
                item.isExpanded = !(item.isExpanded)
                notifyItemChanged(position)
            }
        }
    }
}