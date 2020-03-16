package com.github.hamatoshi.receiptkeeper.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.util.DATE_PATTERN
import com.github.hamatoshi.receiptkeeper.util.DateUtils
import com.github.hamatoshi.receiptkeeper.util.TIME_PATTERN

class InputViewModel : ViewModel() {

    private val currentMills = System.currentTimeMillis()
    val currentDateString = MutableLiveData<String>()
    val currentTimeString = MutableLiveData<String>()

    init {
        currentDateString.value = DateUtils.millsToString(currentMills, DATE_PATTERN)
        currentTimeString.value = DateUtils.millsToString(currentMills, TIME_PATTERN)
    }

    // For navigation to HomeFragment
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome
    fun onFabClicked() { _navigateToHome.value = true }
    fun doneNavigating() { _navigateToHome.value = false }

    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InputViewModel::class.java)) {
                return InputViewModel() as T
            }
            throw IllegalArgumentException("unknown viewModel class")
        }
    }
}