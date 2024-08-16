package com.example.core.domain.usecase

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getNowPlayingMovies() = movieRepository.getNowPlayingMovies()

    override fun getPopularMovies() = movieRepository.getPopularMovies()

    override fun getTopratedMovies() = movieRepository.getTopratedMovies()

    override fun searchMovie(query: String) = movieRepository.searchMovie(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean): Boolean{
        return movieRepository.setFavoriteMovie(movie, state)
    }

    override fun getMovieDetail(id: Int) = movieRepository.getMovieDetail(id)
}