package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    fun getTopratedMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<Resource<List<Movie>>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean): Boolean

    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>

    fun searchMovie(query: String): Flow<Resource<List<Movie>>>
}