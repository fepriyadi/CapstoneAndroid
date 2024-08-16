package com.example.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase

class FavouriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getFavoriteMovie().asLiveData()
}