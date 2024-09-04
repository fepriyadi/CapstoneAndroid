package com.example.capstonemovie.detail

import android.os.Handler
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.example.core.data.Resource
import com.example.core.data.source.remote.response.Cast
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.ui.ActorModel_
import com.example.core.ui.header
import com.example.core.ui.loading
import java.lang.ref.WeakReference

class DetailEpoxyController(private val callbacks: MovieDetailsCallbacks,
                            private var glide: RequestManager?,
                            epoxyHandler: Handler):
    TypedEpoxyController<DetailScreenState>(epoxyHandler, epoxyHandler) {

    interface MovieDetailsCallbacks {
        fun toggleMovieFavouriteStatus(newState: Boolean)
    }
    private val glideRef = WeakReference(glide)
    override fun buildModels(state: DetailScreenState) {
        with(state){
            run {
                buildFavResource(isFavorite)
                movieResource?.let { buildMovieResource(it) }
                castResource?.let { buildCastResource(it) }
            }
        }
    }

    private fun buildFavResource(isFavorite: Boolean) {
        infoBar {
            id("info")
            favouritesClickListener{ _, _, _, _ ->
                this@DetailEpoxyController.callbacks.toggleMovieFavouriteStatus(!isFavorite)
            }
            isFavorite(isFavorite)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }


    private fun buildMovieResource(resource: Resource<MovieDetailResponse>) {
        when (resource) {
            is Resource.Success -> {
                val movie = resource.data
                header {
                    id("${resource.hashCode()}-description")
                    title("Description")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                mainText {
                    id("${resource.hashCode()}-overview")
                    text(movie?.overview.toString())
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Error -> {
                header {
                    id("${resource.hashCode()}-description")
                    title("Description")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                mainText {
                    id("${resource.hashCode()}-overview")
                    text("We can't find a description for this movie")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                header {
                    id("${resource.hashCode()}-description")
                    title("Description")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                loading {
                    id("${resource.hashCode()}-load-description")
                    description("Loading Description")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }

    private fun createCarouselModels(items: List<Resource<Cast>>): List<EpoxyModel<*>> {
        return items.map { actorResource ->
            ActorModel_()
                .id(actorResource.data?.id)
                .actorId(actorResource.data?.id)
                .name(actorResource.data?.name.toString())
                .glide(this@DetailEpoxyController.glideRef.get()!!)
                .pictureUrl(actorResource.data?.profileURl.toString())
                .transitionName("actor-${actorResource.data?.id}")
        }
    }

    private fun buildCastResource(actors: List<Resource<Cast>>) {
        actors.filterIsInstance<Resource.Success<Cast>>()
            .takeIf { it.isNotEmpty() }
            ?.let { actorList ->
                val models = createCarouselModels(actorList)
                header {
                    id("cast")
                    title("Cast")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                CarouselModel_().apply {
                    id("cast-carousel")
                    numViewsToShowOnScreen(this@DetailEpoxyController.spanCount.toFloat().minus(1f).times(1.2f))
                    models(models)
                }.addTo(this)
                

//                carousel {
//                    id("cast-carousel")
//                    numViewsToShowOnScreen(this@DetailEpoxyController.spanCount.toFloat().minus(1f).times(1.2f))
//                    this@carousel.withModelsFrom(models)
//                }
            }
    }
}