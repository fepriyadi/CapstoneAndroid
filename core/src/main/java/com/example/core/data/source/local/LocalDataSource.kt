package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.MovieDao
import com.example.core.domain.model.MovieDetail
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun isFavoriteMovie(movieId: Int): Flow<Boolean> = movieDao.isFavorite(movieId)

    suspend fun setFavoriteMovie(movie: MovieDetail, newState: Boolean): Boolean {
        movie.isFavoriteMovie = newState
        val result = movieDao.updateFavoriteMovie(DataMapper.mapModelToEntity(movie))
        return result > 0
    }
}