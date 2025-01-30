package com.example.capstonemovie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    // Properly initialize the MutableLiveData with an initial value
    private val _homeScreenState = MutableLiveData(HomeScreenState())
    val homeScreenState: LiveData<HomeScreenState> get() = _homeScreenState

    // Perform initialization of the ViewModel
    init {
        fetchMovies()
    }

    // Function to fetch movies using the MovieUseCase
    private fun fetchMovies() {
        // You can initialize state here or set a loading state
        _homeScreenState.value = HomeScreenState(
            popularMoviesResource = Resource.Loading(),
            topRatedMoviesResource = Resource.Loading(),
            nowPlayingResultsResource = Resource.Loading()
        )

        // Fetch popular movies
        launchDataLoad {
            movieUseCase.getPopularMovies()
                .onStart {
                    // Optional: show a loading state for UI
                }
                .catch { exception ->
                    // Handle errors (e.g., network failure)
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        popularMoviesResource = Resource.Error(exception.message ?: "Unknown error")
                    )
                }
                .collect { resource ->
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        popularMoviesResource = resource
                    )
                }
        }

        // Fetch top rated movies
        launchDataLoad {
            movieUseCase.getTopratedMovies()
                .onStart {
                    // Optional: show a loading state for UI
                }
                .catch { exception ->
                    // Handle errors (e.g., network failure)
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        topRatedMoviesResource = Resource.Error(
                            exception.message ?: "Unknown error"
                        )
                    )
                }
                .collect { resource ->
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        topRatedMoviesResource = resource
                    )
                }
        }

        // Fetch now playing movies
        launchDataLoad {
            movieUseCase.getNowPlayingMovies()
                .onStart {
                    // Optional: show a loading state for UI
                }
                .catch { exception ->
                    // Handle errors (e.g., network failure)
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        nowPlayingResultsResource = Resource.Error(
                            exception.message ?: "Unknown error"
                        )
                    )
                }
                .collect { resource ->
                    _homeScreenState.value = _homeScreenState.value?.copy(
                        nowPlayingResultsResource = resource
                    )
                }
        }
    }

    // Helper function to launch data loading in a coroutine scope
    private fun launchDataLoad(dataLoad: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                dataLoad()
            } catch (e: Exception) {
                // Handle errors (network failure, etc.)
                _homeScreenState.value = _homeScreenState.value?.copy(
                    popularMoviesResource = Resource.Error("Failed to load data"),
                    topRatedMoviesResource = Resource.Error("Failed to load data"),
                    nowPlayingResultsResource = Resource.Error("Failed to load data"),
                )
            }
        }
    }
}