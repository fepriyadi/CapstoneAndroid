package com.example.capstonemovie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.domain.usecase.MovieUseCase
import com.example.core.utils.DataMapper
import com.example.core.utils.log
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<MovieDetailResponse>>()

    val dataMovieDetail: LiveData<Resource<MovieDetailResponse>>
        get() = _movieDetail

    fun toggleMovieFavouriteStatus(newStatus: Boolean) {
        dataMovieDetail.value?.data?.let {
            movieUseCase.setFavoriteMovie(DataMapper.mapResponseToEntity(it), newStatus).let { response ->
                if (response){
                    newStatus.toString().log("new status ")
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