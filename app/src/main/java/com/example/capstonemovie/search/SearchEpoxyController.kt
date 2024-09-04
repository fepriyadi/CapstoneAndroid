package com.example.capstonemovie.search

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.header
import com.example.core.ui.infoText
import com.example.core.ui.loading
import com.example.core.ui.movieSearchResult
import java.lang.ref.WeakReference

class SearchEpoxyController(private val callbacks: EpoxyCallbacks,
                            glide: RequestManager?,
                            epoxyHandler: Handler) :
    TypedEpoxyController<Resource<List<Movie>>>(epoxyHandler, epoxyHandler) {
    private val glideRef = WeakReference(glide)

    override fun buildModels(movies: Resource<List<Movie>>) {
        with(movies) {
            run {
                buildSearch(movies)
            }
        }
    }

    private fun buildSearch(searchedMovies: Resource<List<Movie>>?) {
        header {
            id("popular")
            title("Search result")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        searchedMovies?.let {
            when (searchedMovies) {
                is Resource.Success -> {
                    when {
                        searchedMovies.data?.isEmpty() == true -> {
                            infoText {
                                id("no-results-found")
                                text("No movies found for this query")
                                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                            }
                        }
                        else -> searchedMovies.data?.forEach { searchResult ->
                            movieSearchResult {
                                with(searchResult) {
                                    id(id)
                                    movieId(id)
                                    movieTitle(title.toString())
                                    this@SearchEpoxyController.glideRef.get()?.let { glide(it) }
                                    posterUrl(posterURl)
                                    transitionName("poster-$id")
                                    clickListener { model, _, _, _ ->
                                        this@SearchEpoxyController.callbacks.onMovieItemClicked(model.movieId!!)
                                    }
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    infoText {
                        id("error-search-results")
                        text("Error getting search results")
                        spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                    }
                }
                is Resource.Loading -> {
                    loading {
                        id("load-search-results")
                        description("Loading Search results")
                        spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                    }
                }
            }
        }

    }
}