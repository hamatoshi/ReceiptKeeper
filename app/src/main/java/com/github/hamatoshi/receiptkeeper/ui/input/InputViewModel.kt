package com.github.hamatoshi.receiptkeeper.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.data.*
import com.github.hamatoshi.receiptkeeper.util.DATE_PATTERN
import com.github.hamatoshi.receiptkeeper.util.DateUtils
import com.github.hamatoshi.receiptkeeper.util.INITIAL_COMMIT
import com.github.hamatoshi.receiptkeeper.util.TIME_PATTERN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InputViewModel(
    private val receiptId: Long = -1L,
    receiptSummaryDatabaseDao: ReceiptSummaryDatabaseDao,
    receiptContentDatabaseDao: ReceiptContentDatabaseDao
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receiptSummaryRepository = ReceiptSummaryRepository(receiptSummaryDatabaseDao)
    private val receiptContentRepository = ReceiptContentRepository(receiptContentDatabaseDao)

    val currentDateString = MutableLiveData<String>()
    val currentTimeString = MutableLiveData<String>()
    val taxOperation = MutableLiveData<Int>()

    private val _currentReceiptSummary = MutableLiveData<ReceiptSummary>()
    val currentReceiptSummary: LiveData<ReceiptSummary>
        get() = _currentReceiptSummary

    private val _currentReceiptContents = MutableLiveData<List<ReceiptContent>>()
    val currentReceiptContents: LiveData<List<ReceiptContent>>
        get() = _currentReceiptContents

    init {
        initializeCurrentReceiptSummary()
        initializeCurrentReceiptContents()
    }

    // For navigation to HomeFragment
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome
    fun doneNavigating() { _navigateToHome.value = false }

    fun onFabClicked() {
        val inputDateString = "${currentDateString.value} ${currentTimeString.value}"
        val combinedPattern = "$DATE_PATTERN $TIME_PATTERN"
        val inputDate = DateUtils.stringToDate(inputDateString, combinedPattern) ?: return
        uiScope.launch {
            _currentReceiptSummary.value?.date = inputDate.time
            when (receiptId) {
                INITIAL_COMMIT -> receiptSummaryRepository.insert(_currentReceiptSummary.value)
                else -> receiptSummaryRepository.update(_currentReceiptSummary.value)
            }
            _navigateToHome.value = true
        }
    }

    private fun initializeCurrentDateAndTime(mills: Long) {
        currentDateString.value = DateUtils.millsToString(mills, DATE_PATTERN)
        currentTimeString.value = DateUtils.millsToString(mills, TIME_PATTERN)
    }
    private fun initializeCurrentReceiptSummary() {
        when (receiptId) {
            INITIAL_COMMIT -> {
                _currentReceiptSummary.value = ReceiptSummary()
                initializeCurrentDateAndTime(System.currentTimeMillis())
            }
            else -> {
                uiScope.launch {
                    val receiptSummarySelected =
                        receiptSummaryRepository.getReceiptWithId(receiptId) ?: return@launch
                    _currentReceiptSummary.value = receiptSummarySelected
                    initializeCurrentDateAndTime(receiptSummarySelected.date)
                }
            }
        }
    }
    private fun initializeCurrentReceiptContents() {
        if (receiptId != INITIAL_COMMIT) {
            uiScope.launch {
                val receiptContentsSelected =
                    receiptContentRepository.getReceiptContentsWithId(receiptId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(
        private val receiptId: Long = -1L,
        private val receiptSummaryDatabaseDao: ReceiptSummaryDatabaseDao,
        private val receiptContentDatabaseDao: ReceiptContentDatabaseDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InputViewModel::class.java)) {
                return InputViewModel(
                    receiptId,
                    receiptSummaryDatabaseDao,
                    receiptContentDatabaseDao
                ) as T
            }
            throw IllegalArgumentException("unknown viewModel class")
        }
    }
}