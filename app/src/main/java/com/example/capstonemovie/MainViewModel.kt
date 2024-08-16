package com.example.capstonemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.utils.SingleLiveEvent

class MainViewModel : ViewModel() {
    private val _toolbarTitle = SingleLiveEvent<String>()
    private val _isFavourite = SingleLiveEvent<Boolean>()

    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    val isFavourite: LiveData<Boolean>
        get() = _isFavourite

    fun updateToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun toggleMovieFavouriteStatus(newState: Boolean) {
        _isFavourite.value = newState
    }
}