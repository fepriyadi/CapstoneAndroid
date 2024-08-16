package com.example.capstonemovie.home

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.header
import com.example.core.ui.infoText
import com.example.core.ui.loading
import com.example.core.ui.movie
import com.example.core.utils.log

class HomeEpoxyController(private val callbacks: EpoxyCallbacks,
                          private val glide: RequestManager,
                          epoxyHandler: Handler) :
    TypedEpoxyController<HomeScreenState>(epoxyHandler, epoxyHandler) {


    override fun buildModels(state: HomeScreenState) {
        with(state) {
            run {
                buildHomeModel(nowPlayingResultsResource, popularMoviesResource, topRatedMoviesResource)
            }
        }
    }

    private fun buildHomeModel(
        nowPlayingMovies: Resource<List<Movie>>?,
        popularMovies: Resource<List<Movie>>?,
        topRatedMovies: Resource<List<Movie>>?
    ) {
        header {
            id("nowplaying")
            title("Now Playing")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        when (nowPlayingMovies) {
            is Resource.Success -> {
                nowPlayingMovies.data?.forEach { nowPlayingMovie ->
                    movie {
                        id(nowPlayingMovie.id)
                        movieId(nowPlayingMovie.id)
                        posterUrl(nowPlayingMovie.posterURl)
                        glide(this@HomeEpoxyController.glide)
                        transitionName("poster-${nowPlayingMovie.id}")
                        clickListener { model, _, _, _ ->
                            this@HomeEpoxyController.callbacks.onMovieItemClicked(
                                model.movieId!!
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                infoText {
                    id("error-popular-movies")
                    text("Error getting Popular movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            is Resource.Loading -> {
                loading {
                    id("load-popular-movies")
                    description("Loading NowPlaying movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            null -> Unit
        }

        header {
            id("popular")
            title("Popular")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        when (popularMovies) {
            is Resource.Success -> {
                popularMovies.data?.forEach { popularMovie ->
                    movie {
                        id(popularMovie.id)
                        movieId(popularMovie.id)
                        posterUrl(popularMovie.posterURl)
                        glide(this@HomeEpoxyController.glide)
                        transitionName("poster-${popularMovie.id}")
                        clickListener { model, _, _, _ ->
                            this@HomeEpoxyController.callbacks.onMovieItemClicked(
                                model.movieId!!
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                infoText {
                    id("error-popular-movies")
                    text("Error getting Popular movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            is Resource.Loading -> {
                loading {
                    id("load-popular-movies")
                    description("Loading Popular movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            null -> Unit
        }

        header {
            id("toprated")
            title("Top rated")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        when (topRatedMovies) {
            is Resource.Success -> {
                topRatedMovies.data?.forEach { topratedMovie ->
                    movie {
                        id(topratedMovie.id)
                        movieId(topratedMovie.id)
                        posterUrl(topratedMovie.posterURl)
                        glide(this@HomeEpoxyController.glide)
                        transitionName("poster-${topratedMovie.id}")
                        clickListener { model, _, _, _ ->
                            this@HomeEpoxyController.callbacks.onMovieItemClicked(
                                model.movieId!!
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                infoText {
                    id("error-popular-movies")
                    text("Error getting Popular movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            is Resource.Loading -> {
                loading {
                    id("load-popular-movies")
                    description("Loading Top rated movies")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }

            null -> Unit
        }
    }
}