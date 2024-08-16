package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun isFavoriteMovie(movieId: Int): Flow<Boolean> = movieDao.isFavorite(movieId)

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean): Boolean {
        movie.isFavorite = newState
        val result = movieDao.updateFavoriteMovie(movie)
        return result > 0
    }
}