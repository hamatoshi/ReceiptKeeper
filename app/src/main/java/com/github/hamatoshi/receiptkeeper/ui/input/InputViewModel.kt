package com.github.hamatoshi.receiptkeeper.ui.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InputViewModel : ViewModel() {

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