package com.example.capstonemovie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.MovieUseCase
import com.example.core.utils.log
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private var _state = MutableLiveData<HomeScreenState>()

    val state: LiveData<HomeScreenState>
        get() = _state

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        try {
            viewModelScope.launch {
                val nowPlayingMovies = movieUseCase.getNowPlayingMovies()
                val popularMovies = movieUseCase.getPopularMovies()
                val topRatedMovies = movieUseCase.getTopratedMovies()

                combine(
                    nowPlayingMovies,
                    popularMovies,
                    topRatedMovies
                ) { popular, toprated, nowplaying ->
                    HomeScreenState(
                        popularMoviesResource = popular,
                        topRatedMoviesResource = toprated,
                        nowPlayingResultsResource = nowplaying
                    )
                }.collect { state ->
                    _state.postValue(state)
                }

            }
        } catch (e: Exception) {
            // Handle exceptions
            toString().log("Error collecting Flow $e")
        }

    }
}