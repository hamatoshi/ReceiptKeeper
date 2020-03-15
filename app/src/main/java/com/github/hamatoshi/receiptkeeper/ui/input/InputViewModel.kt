package com.github.hamatoshi.receiptkeeper.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InputViewModel : ViewModel() {

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