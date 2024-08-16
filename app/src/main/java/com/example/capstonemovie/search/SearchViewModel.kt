package com.example.capstonemovie.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<Movie>>>()

    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    fun searchMovie(query: String) {
        viewModelScope.launch {
            movieUseCase.searchMovie(query)
                .collect { movie ->
                    _movies.value = movie
                }
        }
    }
}