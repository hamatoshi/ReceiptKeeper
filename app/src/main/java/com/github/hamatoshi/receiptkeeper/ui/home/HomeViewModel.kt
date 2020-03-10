package com.github.hamatoshi.receiptkeeper.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.data.Receipt
import com.github.hamatoshi.receiptkeeper.data.ReceiptContentsStore
import com.github.hamatoshi.receiptkeeper.data.ReceiptStore
import com.github.hamatoshi.receiptkeeper.data.ReceiptUtil

class HomeViewModel : ViewModel() {

    private val _receipts = MutableLiveData<List<Receipt>>()
    val receipts : LiveData<List<Receipt>>
        get() = _receipts

    init {
        val currentReceipts = ReceiptStore.allReceipts
        currentReceipts[0].contents = ReceiptContentsStore.receiptContentsSevenEleven
        currentReceipts[0].total = ReceiptUtil.total(currentReceipts[0])
        currentReceipts[1].contents = ReceiptContentsStore.receiptContentsOlympic
        currentReceipts[1].total = ReceiptUtil.total(currentReceipts[1])
        currentReceipts[2].contents = ReceiptContentsStore.receiptContentsTokyuStore
        currentReceipts[2].total = ReceiptUtil.total(currentReceipts[2])
        currentReceipts[3].contents = ReceiptContentsStore.receiptContentsFamilyMart
        currentReceipts[3].total = ReceiptUtil.total(currentReceipts[3])
        _receipts.value = currentReceipts
    }

    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel() as T
            }
            throw IllegalArgumentException("unknown viewModel class")
        }
    }

}