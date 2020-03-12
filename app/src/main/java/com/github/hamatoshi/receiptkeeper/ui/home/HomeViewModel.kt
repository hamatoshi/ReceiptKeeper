package com.github.hamatoshi.receiptkeeper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(
    receiptSummaryDatabaseDao: ReceiptSummaryDatabaseDao,
    receiptContentDatabaseDao: ReceiptContentDatabaseDao
) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receiptSummaryRepository = ReceiptSummaryRepository(receiptSummaryDatabaseDao)
    private val receiptContentRepository = ReceiptContentRepository(receiptContentDatabaseDao)

    private val _receipts = MutableLiveData<List<ReceiptSummary>>()
    val receipts : LiveData<List<ReceiptSummary>>
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

    class Factory(
        private val receiptSummaryDatabaseDao: ReceiptSummaryDatabaseDao,
        private val receiptContentDatabaseDao: ReceiptContentDatabaseDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(receiptSummaryDatabaseDao, receiptContentDatabaseDao) as T
            }
            throw IllegalArgumentException("unknown viewModel class")
        }
    }

}