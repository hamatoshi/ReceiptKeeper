package com.github.hamatoshi.receiptkeeper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.data.*
import com.github.hamatoshi.receiptkeeper.util.DateUtils
import com.github.hamatoshi.receiptkeeper.util.INITIAL_COMMIT
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

    private val currentTimeMills = System.currentTimeMillis()
    private val fromDateLong = DateUtils.millsOfFrom(currentTimeMills)
    private val toDateLong = DateUtils.millsOfTo(currentTimeMills)

    val receipts = receiptSummaryRepository.getReceiptBetweenDate(fromDateLong, toDateLong)


    // For navigation to InputFragment
    private val _navigateToInput = MutableLiveData<Long>()
    val navigateToInput: LiveData<Long>
        get() = _navigateToInput

    init {
//        val currentReceipts = ReceiptStore.allReceipts
//        currentReceipts[0].contents = ReceiptContentsStore.receiptContentsSevenEleven
//        currentReceipts[1].contents = ReceiptContentsStore.receiptContentsOlympic
//        currentReceipts[2].contents = ReceiptContentsStore.receiptContentsTokyuStore
//        currentReceipts[3].contents = ReceiptContentsStore.receiptContentsFamilyMart
//        _receipts.value = currentReceipts
    }


    fun doneNavigating() { _navigateToInput.value = null }
    fun onFabClicked() { _navigateToInput.value = INITIAL_COMMIT }
    fun onEditClicked(receiptId: Long) { _navigateToInput.value = receiptId }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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