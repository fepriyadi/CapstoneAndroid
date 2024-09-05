package com.example.capstonemovie.detail

import com.example.core.data.Resource
import com.example.core.domain.model.CastDomain
import com.example.core.domain.model.MovieDetail

data class DetailScreenState(
    val isFavorite:Boolean = false,
    val movieResource: Resource<MovieDetail>?,
    val castResource: List<Resource<CastDomain>>?,
)