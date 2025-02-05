package com.example.capstonemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.utils.SingleLiveEvent

class MainViewModel : ViewModel() {
    private val _toolbarTitle = SingleLiveEvent<String>()

    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    fun updateToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }
}