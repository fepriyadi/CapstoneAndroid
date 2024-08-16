package com.example.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import kotlin.math.abs

object DataMapper {
    fun mapResponseToEntity(input: MovieDetailResponse): MovieEntity{
        return MovieEntity(
            id = input.id,
            title = input.title,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            overview = input.overview,
            year = input.releaseDate.toString(),
            duration = input.runtime?.toFormattedRuntime().toString(),
            rating = input.voteAverage.toAsteriskRating(),
            genre = input.genres.first().name,
            isFavorite = input.isFavoriteMovie)
    }

    fun mapResponseToModel(input: List<MovieResponse>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map {
            val movie = Movie(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                overview = it.overview,
                genre = "",
                year = it.releaseDate.formatYear().toString(),
                duration = "",
                rating = it.voteAverage.toAsteriskRating(),
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                overview = it.overview,
                genre = "",
                year = it.releaseDate.formatYear().toString(),
                duration = "",
                rating = it.voteAverage.toAsteriskRating(),
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                overview = it.overview,
                genre = it.genre,
                year = it.year,
                duration = it.duration,
                rating = it.rating,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(it: Movie) = MovieEntity(
        id = it.id,
        title = it.title,
        backdropPath = it.backdropPath,
        posterPath = it.posterPath,
        overview = it.overview,
        genre = it.genre.toString(),
        year = it.year.toString(),
        duration = it.duration.toString(),
        rating = it.rating,
        isFavorite = it.isFavorite
    )
}