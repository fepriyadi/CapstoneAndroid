package com.example.capstonemovie.detail

import com.example.core.data.Resource
import com.example.core.data.source.remote.response.Cast
import com.example.core.data.source.remote.response.MovieDetailResponse

data class DetailScreenState(
    val isFavorite:Boolean = false,
    val movieResource: Resource<MovieDetailResponse>?,
    val castResource: List<Resource<Cast>>?,
)