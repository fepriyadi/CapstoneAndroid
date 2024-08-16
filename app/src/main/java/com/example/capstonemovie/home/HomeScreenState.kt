package com.example.capstonemovie.home

import com.example.core.data.Resource
import com.example.core.domain.model.Movie

data class HomeScreenState(
    val popularMoviesResource: Resource<List<Movie>>? = null,
    val topRatedMoviesResource: Resource<List<Movie>>? = null,
    val nowPlayingResultsResource: Resource<List<Movie>>? = null
)
