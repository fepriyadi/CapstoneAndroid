package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.Cast
import com.example.core.data.source.remote.response.Credits
import com.example.core.data.source.remote.response.Crew
import com.example.core.data.source.remote.response.Genre
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.Video
import com.example.core.domain.model.CastDomain
import com.example.core.domain.model.CreditsDomain
import com.example.core.domain.model.CrewDomain
import com.example.core.domain.model.GenreDomain
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.VideoDomain

object DataMapper {
    fun mapModelToEntity(input: MovieDetail): MovieEntity{
        return MovieEntity(
            id = input.id,
            title = input.title,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            overview = input.overview,
            year = input.releaseDate,
            duration = input.runtime?.toFormattedRuntime().toString(),
            rating = input.rating,
            genre = input.genreName,
            isFavorite = input.isFavoriteMovie)
    }

    fun mapResponseToModel(input: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = input.id,
            title = input.title,
            overview = input.overview ?: "",
            releaseDate = input.releaseDate ?: "",
            runtime = input.runtime,
            rating = input.voteAverage.format("%.2f"),
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            videos = input.videos?.results?.map { it.toDomain() } ?: emptyList(),
            credits = input.credits?.toDomain() ?: CreditsDomain(emptyList(), emptyList()),
            genres = input.genres.map { it.toDomain() }
        )
    }

    fun mapResponseToModel(input: List<MovieResponse>): List<Movie> {
        val movieList = ArrayList<Movie>()
        toString().log("mapresponse ${movieList.size}")
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

    private fun Video.toDomain(): VideoDomain {
        return VideoDomain(
            id = this.id,
            key = this.key,
            name = this.name,
            site = this.site,
            type = this.type
        )
    }

    private fun Credits.toDomain(): CreditsDomain {
        return CreditsDomain(
            cast = this.cast.map { it.toDomain() },
            crew = this.crew.map { it.toDomain() }
        )
    }

    private fun Cast.toDomain(): CastDomain {
        return CastDomain(
            id = this.id,
            character = this.character,
            name = this.name,
            profilePath = this.profilePath
        )
    }

    private fun Crew.toDomain(): CrewDomain {
        return CrewDomain(
            id = this.id,
            job = this.job,
            name = this.name,
            profilePath = this.profilePath
        )
    }

    private fun Genre.toDomain(): GenreDomain {
        return GenreDomain(
            id = this.id,
            name = this.name
        )
    }


}