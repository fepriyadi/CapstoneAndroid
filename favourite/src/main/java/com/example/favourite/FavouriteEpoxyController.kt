package com.example.favourite

import android.os.Handler
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.InfoTextModel_
import com.example.core.ui.header
import com.example.core.ui.movie
import java.lang.ref.WeakReference

class FavouriteEpoxyController(private val callbacks: EpoxyCallbacks,
                               glide: RequestManager?,
                               epoxyHandler: Handler):
    TypedEpoxyController<Resource<List<Movie>>>(epoxyHandler, epoxyHandler) {

    @AutoModel
    lateinit var emptyListModelFavourited: InfoTextModel_

    private val glideRef = WeakReference(glide)

    override fun buildModels(movieList: Resource<List<Movie>>) {
        run {
            buildFavouritesModel(movieList)
        }
    }

    private fun buildFavouritesModel(favouriteMoviesResource: Resource<List<Movie>>) {
        header {
            id("favourites")
            title("Favourites")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        when (favouriteMoviesResource) {
            is Resource.Success -> {
                when {
                    favouriteMoviesResource.data?.isEmpty() == true -> {
                        emptyListModelFavourited
                            .text("You have not favourited any movies yet.")
                            .spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                            .also { this.add(it) }
                        Unit
                    }

                    else -> favouriteMoviesResource.data?.forEach { favouriteMovie ->
                        movie {
                            id(favouriteMovie.id)
                            movieId(favouriteMovie.id)
                            this@FavouriteEpoxyController.glideRef.get()?.let { glide(it) }
                            posterUrl(favouriteMovie.posterURl)
                            transitionName("poster-${favouriteMovie.id}")
                            clickListener { model, _, _, _ ->
                                this@FavouriteEpoxyController.callbacks.onMovieItemClicked(
                                    model.movieId!!
                                )
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
//                infoText {
//                    id("error-favourite-movies")
//                    text("Error getting Favourite movies")
//                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
//                }
            }
            is Resource.Loading -> {
//                loading {
//                    id("load-favourite-movies")
//                    description("Loading Favourite movies")
//                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
//                }
            }
        }
    }
}