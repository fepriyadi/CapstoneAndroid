package com.example.capstonemovie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()

    val dataMovieDetail: LiveData<Resource<MovieDetail>>
        get() = _movieDetail

    fun toggleMovieFavouriteStatus(newStatus: Boolean) {
        dataMovieDetail.value?.data?.let {
            movieUseCase.setFavoriteMovie(it, newStatus).let { response ->
                if (response){
                    it.isFavoriteMovie = newStatus
                    _movieDetail.postValue(Resource.Success(it))
                }
            }
        }

    }

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            movieUseCase.getMovieDetail(id).collect { response ->
                _movieDetail.postValue(response)
            }
        }

    }
}